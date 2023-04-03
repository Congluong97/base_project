package com.pacom.baseproject.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceProvider @Inject constructor(var context: Context) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
}