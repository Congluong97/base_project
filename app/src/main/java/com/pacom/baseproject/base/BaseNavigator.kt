package com.pacom.baseproject.base

import android.util.Log
import com.pacom.baseproject.mode.ApiException

interface BaseNavigator {
    fun handleError(throwable: Throwable){
        if(throwable is ApiException){
            handleApiError(throwable)
        }else{
            handleException(throwable.message)
        }
    }

    fun handleApiError(apiException: ApiException){}
    fun handleException(message: String?){}
}