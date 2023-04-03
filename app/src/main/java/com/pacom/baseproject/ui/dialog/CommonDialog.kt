package com.pacom.baseproject.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.pacom.baseproject.R
import com.pacom.baseproject.base.BaseDialog
import com.pacom.baseproject.databinding.AlertDialogLayoutBinding

class CommonDialog(
    context: Context,
    var title: String?,
    var buttonOk: String?,
    var buttonCancel: String?,
    var message: String?,
    var onClickOk: (() -> Unit)? = null,
    var onClickCancel: (() -> Unit)? = null,
    var isShowButton: Boolean = true,
    var align: Int = View.TEXT_ALIGNMENT_CENTER
) : BaseDialog<AlertDialogLayoutBinding>(context) {

    override fun getLayoutRes(): Int {
        return R.layout.alert_dialog_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMarginDialog()
        title?.let {
            mViewBinding.tvTitle.text = it
            mViewBinding.tvTitle.visibility = View.VISIBLE
        }

        if (!isShowButton) {
            mViewBinding.textView45.visibility = View.INVISIBLE
            mViewBinding.btnCancel.visibility = View.INVISIBLE
        } else {
            buttonOk?.let {
                mViewBinding.textView45.text = it
            }

            buttonCancel?.let {
                mViewBinding.btnCancel.text = it
                mViewBinding.btnCancel.visibility = View.VISIBLE
            }
        }
        mViewBinding.tvMessage.text = message
        mViewBinding.tvMessage.textAlignment = align
        mViewBinding.textView45.setOnClickListener {
            dismiss()
            onClickOk?.invoke()
        }

        mViewBinding.btnCancel.setOnClickListener {
            dismiss()
            onClickCancel?.invoke()
        }
    }
}