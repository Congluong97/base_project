package com.pacom.baseproject.mode.reponse


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstantResponse(
    @SerialName("result")
    var result: Boolean = false, // true
    @SerialName("status")
    var status: Statuss? = null,
    @SerialName("body")
    var body: Bodys? = null,
)

@Serializable
data class Statuss(
    @SerialName("code")
    var code: Int = 0, // 200
    @SerialName("message")
    var message: String = "" // 成功
)

@Serializable
data class Bodys(
    @SerialName("idToken") var idToken: String,
    @SerialName("accessToken") var accessToken: String,
    @SerialName("refreshToken") var refreshToken: String,
)