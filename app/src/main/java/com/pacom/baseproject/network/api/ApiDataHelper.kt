package com.pacom.baseproject.network.api

import com.pacom.baseproject.mode.reponse.ConstantResponse
import com.pacom.baseproject.mode.reponse.TestResponse
import kotlinx.coroutines.flow.Flow

interface ApiDataHelper {
    suspend fun getConstant(): Flow<ConstantResponse>
}