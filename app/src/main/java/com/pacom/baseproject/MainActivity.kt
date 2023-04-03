package com.pacom.baseproject

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pacom.baseproject.base.BaseActivity
import com.pacom.baseproject.databinding.ActivityMainBinding
import com.pacom.baseproject.mode.ApiException
import com.pacom.baseproject.ui.list.ListActivity
import com.pacom.baseproject.ui.test_fragment.TestFragment
import com.pacom.baseproject.utils.informationScreen
import com.pacom.baseproject.utils.showCommonDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {
    override fun getViewModelClass() = MainViewModel::class.java

    override fun getLayoutId() = R.layout.activity_main

    override fun getBindingVariable() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)
        this.informationScreen()
        addFragment(TestFragment.newInstance(), R.id.container)

        mViewModel.liveData.observe(this) { data ->
            Log.d(TAG, "onCreate: $data")
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                mViewModel.callApi()
            }
        }
    }

    override fun handleApiError(apiException: ApiException) {
        displayApiError(apiException)
    }

    override fun handleException(message: String?) {
        displayError(message)
    }
}