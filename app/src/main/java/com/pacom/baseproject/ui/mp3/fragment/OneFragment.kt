package com.pacom.baseproject.ui.mp3.fragment

import android.os.Bundle
import android.view.View
import com.pacom.baseproject.R
import com.pacom.baseproject.base.BaseFragment
import com.pacom.baseproject.databinding.FragmentOneBinding
import com.pacom.baseproject.ui.mp3.Mp3Navigator
import com.pacom.baseproject.ui.mp3.Mp3ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneFragment : BaseFragment<FragmentOneBinding, Mp3ViewModel>(), Mp3Navigator {
    override fun getViewModelClass() = Mp3ViewModel::class.java

    override fun getLayoutId() = R.layout.fragment_one

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.setNavigator(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            OneFragment()
    }
}