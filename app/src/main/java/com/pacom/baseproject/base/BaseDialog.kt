package com.pacom.baseproject.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.pacom.baseproject.utils.screenWidth

abstract class BaseDialog<V: ViewBinding>(context: Context) : AlertDialog(context) {
    lateinit var mViewBinding: V

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutRes(), null, false)
        setContentView(mViewBinding.root)
    }

    fun setMarginDialog(){
        val screenWidth = context.screenWidth()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = (screenWidth * 0.8).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams
    }

    fun setFullScreen(){
        val screenWidth = context.screenWidth()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams
    }
}