package com.pacom.baseproject.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pacom.baseproject.network.AppDataManager
import java.lang.ref.WeakReference


abstract class BaseViewModel<N> constructor(
    private var appDataManager: AppDataManager,
) : ViewModel() {
    private var mNavigator: WeakReference<N>? = null
    var mViewLoading = MutableLiveData<Boolean>()

    fun getNavigator() = mNavigator?.get()

    fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }

    fun showLoading() {
        if (mViewLoading.value != true) {
            mViewLoading.value = true
        }
    }

    fun hideLoading() {
        mViewLoading.value = false
    }
}