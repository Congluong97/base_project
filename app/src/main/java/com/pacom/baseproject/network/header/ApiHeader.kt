package com.pacom.baseproject.network.header

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class ApiHeader @Inject constructor(
    var protectedApiHeader: ProtectedApiHeader,
    var publicApiHeader: PublicApiHeader)

class ProtectedApiHeader @Inject constructor(token:String){
    @Expose
    @SerializedName("Content-Type")
    private val contentType = "application/json"

    @Expose
    @SerializedName("Accept-Encoding")
    private val acceptEncode = "UTF-8"

    @Expose
    @SerializedName("Authorization")
    var token: String? = token
}

class PublicApiHeader{
    @Expose
    @SerializedName("Content-Type")
    private val contentType = "application/json"

    @Expose
    @SerializedName("Accept-Encoding")
    private val acceptEncode = "UTF-8"
}