package com.pacom.baseproject.utils

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("onSingleClick")
fun onSingleClick(view: View, onSingleClickListener: View.OnClickListener) {
    view.setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            onSingleClickListener.onClick(v)
        }
    })
}