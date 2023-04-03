package com.pacom.baseproject.ui.test_fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import com.pacom.baseproject.MainNavigator
import com.pacom.baseproject.MainViewModel
import com.pacom.baseproject.R
import com.pacom.baseproject.base.BaseFragment
import com.pacom.baseproject.databinding.FragmentTestBinding
import com.pacom.baseproject.ui.list.ListActivity
import com.pacom.baseproject.ui.mp3.Mp3Activity
import com.pacom.baseproject.ui.shape.ShapeActivity
import com.pacom.baseproject.utils.showCommonDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : BaseFragment<FragmentTestBinding, MainViewModel>(), MainNavigator {

    override fun getViewModelClass() = MainViewModel::class.java

    override fun getLayoutId() = R.layout.fragment_test


    companion object {
        @JvmStatic
        fun newInstance() = TestFragment()
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.setNavigator(this)
    }

    override fun openListFragment() {
        super.openListFragment()
        //showActivity(ListActivity::class.java)
    }

    override fun openDialog() {
        super.openDialog()
        context?.let {
            showCommonDialog(
                context = it,
                title = "Popup Title",
                message = "Popup Description",
                btnOk = "Ok",
                btnCancel = "Cancel"
            )
        }
    }

    override fun openShape() {
        //showActivity(ShapeActivity::class.java)
    }

    override fun mp3() {
        showActivity<Mp3Activity>()
        //showActivity(Mp3Activity::class.java)
    }

}