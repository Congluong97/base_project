package com.pacom.baseproject.mode.reponse

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>{
    @SerializedName("result") var result: Boolean? = null
    @SerializedName("status") var status: Status? = null
    @SerializedName("body") var body: T? = null
}

data class Status(
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message: String
)