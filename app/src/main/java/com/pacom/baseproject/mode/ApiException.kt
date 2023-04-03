package com.pacom.baseproject.mode

data class ApiException(
    val bodyMessage: String?,
    val errorDetail: String?,
    val bodyCode: Int?,
    val statusCode: Int,
) : Throwable()