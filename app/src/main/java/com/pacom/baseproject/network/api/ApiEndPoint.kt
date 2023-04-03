package com.pacom.baseproject.network.api

import android.support.multidex.BuildConfig


fun getEndPointUrl(suffix: String): String{
    return "$suffix"
}

const val ENPOINT_LOGIN = ""