package com.pacom.baseproject.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pacom.baseproject.BR
import com.pacom.baseproject.utils.dismissLoading
import com.pacom.baseproject.utils.showLoading

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment() {
    protected var mActivity: BaseActivity<*, *>? = null
    protected lateinit var mViewBinding: T
    protected val mViewModel: V by lazy { ViewModelProvider(this).get(getViewModelClass()) }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            mActivity = activity as BaseActivity<*, *>
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    abstract fun getViewModelClass(): Class<V>


    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding?.apply {
            setVariable(BR.viewModel, mViewModel)
            lifecycleOwner = this@BaseFragment
            executePendingBindings()
        }
        setObserverLoading()
    }



    fun setObserverLoading() {
        mViewModel.mViewLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                mActivity?.let { act -> showLoading(act) }
            } else {
                dismissLoading()
            }
        })
    }

    inline fun <reified T: Activity>showActivity() {
        `access$mActivity`?.showActivity<T>()
    }

    @PublishedApi
    internal var `access$mActivity`: BaseActivity<*, *>?
        get() = mActivity
        set(value) {
            mActivity = value
        }


}