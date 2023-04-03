package com.pacom.baseproject.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.pacom.baseproject.R
import com.pacom.baseproject.ui.dialog.CommonDialog

private var sAlert: AlertDialog? = null
private var commonDialog: CommonDialog? = null
fun showLoading(context: Context) {
    if(loadingIsShow()) return
    val dialogBuilder = AlertDialog.Builder(context)
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val dialogView: View = layoutInflater.inflate(R.layout.progress_dialog, null)

    dialogBuilder.setView(dialogView)
    dialogBuilder.setCancelable(false)
    sAlert = dialogBuilder.create()
    sAlert?.show()
}

fun loadingIsShow(): Boolean{
    sAlert?.let {
        if (it.isShowing) {
            return true
        }
    }
    return false
}

fun dismissLoading() {
    sAlert?.let {
        if (it.isShowing) {
            it.dismiss()
        }
    }
}

fun showCommonDialog(
    context: Context,
    title: String? = null,
    btnOk: String = "Ok",
    btnCancel: String? = null,
    message: String = "",
    onClickOk: (() -> Unit)? = null,
    onClickCancel: (() -> Unit)? = null,
    isShowButton: Boolean = true,
    textAlign: Int = View.TEXT_ALIGNMENT_CENTER,
) {
    dismissLoading()
    commonDialog?.let {
        if (it.isShowing) {
            return
        }
    }
    commonDialog = CommonDialog(
        context = context,
        title = title,
        buttonOk = btnOk,
        buttonCancel = btnCancel,
        message = message,
        onClickOk = onClickOk,
        onClickCancel = onClickCancel,
        isShowButton = isShowButton,
        align = textAlign
    ).apply {
        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
    }
}
