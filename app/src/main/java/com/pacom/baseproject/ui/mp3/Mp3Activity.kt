package com.pacom.baseproject.ui.mp3

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.pacom.baseproject.BR
import com.pacom.baseproject.R
import com.pacom.baseproject.base.BaseActivity
import com.pacom.baseproject.base.BaseViewPagerAdapter
import com.pacom.baseproject.databinding.ActivityMp3Binding
import com.pacom.baseproject.ui.mp3.fragment.OneFragment
import com.pacom.baseproject.ui.mp3.fragment.ThreeFragment
import com.pacom.baseproject.ui.mp3.fragment.TwoFragment
import com.pacom.baseproject.utils.convertSecondToTime
import com.pacom.baseproject.utils.dpToPx
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "Mp3Activity"

@AndroidEntryPoint
class Mp3Activity : BaseActivity<ActivityMp3Binding, Mp3ViewModel>(), Mp3Navigator {
    override fun getViewModelClass() = Mp3ViewModel::class.java
    override fun getLayoutId() = R.layout.activity_mp3

    override fun getBindingVariable() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)
        initViewPager()
        initSeekbar()
    }

    private fun initViewPager() {
        val listFragment = arrayListOf(
            OneFragment(),
            TwoFragment(),
            ThreeFragment()
        )
        val adapter = BaseViewPagerAdapter(this, listFragment)
        mViewBinding.viewPager.adapter = adapter
        mViewBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                val progress = if (position == 1) 1 - positionOffset else positionOffset
                Log.d(TAG, "onPageScrolled: $position, $positionOffset, $positionOffsetPixels")
                if (progress != 0f) {
                    mViewBinding.constraintLayout6.progress = progress
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d(TAG, "onPageSelected: $position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.d(TAG, "onPageScrollStateChanged: $state")
            }
        })

        mViewBinding.tableLayout.attachTo(mViewBinding.viewPager)
    }

    private fun initSeekbar() {
        mViewBinding.seekBar.max = 270
        val thumbView =
            LayoutInflater.from(this).inflate(R.layout.layout_seekbar_thumb, null, false)
        mViewBinding.seekBar.thumb = getThumb(0, thumbView)
        mViewBinding.seekBar.thumbOffset = 8f.dpToPx(this).toInt()
        mViewBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mViewBinding.seekBar.thumb = getThumb(progress, thumbView)
                if(progress < seekBar!!.max / 2) {
                    mViewBinding.seekBar.thumbOffset = 8f.dpToPx(this@Mp3Activity).toInt()
                }else{
                    mViewBinding.seekBar.thumbOffset = -(0f.dpToPx(this@Mp3Activity).toInt())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun getThumb(progress: Int, thumbView: View): Drawable? {
        val total = mViewBinding.seekBar.max.convertSecondToTime()
        val pr = progress.convertSecondToTime()
        val tv = (thumbView.findViewById<TextView>(R.id.textView6))
        tv.text = "$pr / $total"

        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(
            thumbView.measuredWidth,
            thumbView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        thumbView.layout(0 , 0, thumbView.measuredWidth , thumbView.measuredHeight)
        thumbView.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }
}