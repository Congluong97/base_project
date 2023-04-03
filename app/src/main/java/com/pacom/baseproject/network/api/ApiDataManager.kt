package com.pacom.baseproject.network.api

import com.pacom.baseproject.mode.reponse.ConstantResponse
import com.pacom.baseproject.mode.reponse.TestResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import kotlin.collections.HashMap

class ApiDataManager @Inject constructor(private var httpClient: HttpClient) :
    ApiDataHelper {

    override suspend fun getConstant(): Flow<ConstantResponse> {
        val key = generateSha256()
        val request = HashMap<String, String>()
        request["hash"] = key
        return flow {
            val result = httpClient.post<ConstantResponse> {
                url("https://app.develop.kayoinoba.org/any/api/anonymous")
                body = request
            }
            emit(result)
        }.flowOn(Dispatchers.IO)

    }

    private fun generateSha256(): String {
        return try {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
            val simpleFormatDate = SimpleDateFormat("yyyyMMddHHmm")
            val date = simpleFormatDate.format(Date())
            val message = "kynbaoioa$date"

            val mac = Mac.getInstance("HmacSHA256")
            val secretKey = SecretKeySpec("ncgg".toByteArray(Charsets.UTF_8), "HmacSHA256")
            mac.init(secretKey)
            val hmacSha256 = mac.doFinal(message.toByteArray(Charsets.UTF_8))
            Base64.getEncoder().encodeToString(hmacSha256)

        } catch (e: RuntimeException) {
            ""
        }
    }
}