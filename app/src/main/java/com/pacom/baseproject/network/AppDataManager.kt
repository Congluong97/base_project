package com.pacom.baseproject.network

import com.pacom.baseproject.mode.reponse.ConstantResponse
import com.pacom.baseproject.mode.reponse.TestResponse
import com.pacom.baseproject.network.api.ApiDataHelper
import com.pacom.baseproject.network.api.ApiDataManager
import com.pacom.baseproject.network.pref.PrefDataManager
import com.pacom.baseproject.network.pref.PrefHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppDataManager @Inject constructor(
    var apiDataHelper: ApiDataManager,
    var prefHelper: PrefDataManager
) :AppDataHelper{

    override fun getAccessToken(): String {
        return prefHelper.getAccessToken()
    }

    override suspend fun getConstant(): Flow<ConstantResponse> {
        return apiDataHelper.getConstant()
    }
}