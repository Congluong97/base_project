package com.pacom.baseproject.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.pacom.baseproject.R
import com.pacom.baseproject.mode.ApiException
import com.pacom.baseproject.utils.dismissLoading
import com.pacom.baseproject.utils.showCommonDialog
import com.pacom.baseproject.utils.showLoading
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel<*>>: AppCompatActivity() {
    protected lateinit var mViewBinding: T
    val mViewModel: V by lazy { ViewModelProvider(this).get(getViewModelClass()) }
    abstract fun getViewModelClass(): Class<V>
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getBindingVariable(): Int

    override fun attachBaseContext(newBase: Context?) {
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        setObserverLoading()
    }

    open fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun performDataBinding() {
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewBinding.let {
            it.setVariable(getBindingVariable(), mViewModel)
            it.executePendingBindings()
        }
    }

    fun gotoBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

     inline fun <reified T: Activity>showActivity(bundle: Bundle? = null) {
        Intent(this, T::class.java).apply {
            bundle?.let {
                putExtra("extra", bundle)
            }
            startActivity(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

     inline fun <reified T: Activity>showActivityWithFlag(bundle: Bundle? = null) {
        Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            bundle?.let {
                putExtra("extra", it)
            }
            startActivity(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun getDataIntent(): Bundle? {
        return intent.getBundleExtra("extra")
    }

    inline fun <reified T> Bundle.getDataOrNull(DATA_KEY: String): T?{
        return this.getParcelable(DATA_KEY) as? T
    }
    
    fun finishActivity() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun setObserverLoading() {
        mViewModel.mViewLoading.observe(this, Observer {
            if (it) {
                showLoading(this)
            } else {
                dismissLoading()
            }
        })
    }

    fun addFragment(fragment: Fragment, resource: Int){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(resource, fragment)
        }
    }

    fun displayApiError(apiException: ApiException){
        when(apiException.bodyCode){
            401 -> {}
            null -> {}
            else ->{}
        }

        showCommonDialog(
            context = this,
            message = apiException.bodyMessage ?: ""
        )
    }

    fun displayError(message: String?){
        showCommonDialog(
            context = this,
            message = message ?: ""
        )
    }

}