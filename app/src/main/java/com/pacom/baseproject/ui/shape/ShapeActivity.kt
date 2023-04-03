package com.pacom.baseproject.ui.shape

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.shape.MaterialShapeDrawable
import com.pacom.baseproject.BR
import com.pacom.baseproject.R
import com.pacom.baseproject.base.BaseActivity
import com.pacom.baseproject.databinding.ActivityShapeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShapeActivity : BaseActivity<ActivityShapeBinding, ShapeViewModel>(), ShapeNavigator {
    override fun getViewModelClass(): Class<ShapeViewModel> = ShapeViewModel::class.java

    override fun getLayoutId() = R.layout.activity_shape

    override fun getBindingVariable() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)

        val background = mViewBinding.view2.background as MaterialShapeDrawable
        background.setTint(ContextCompat.getColor(this, R.color.purple_200))
    }
}