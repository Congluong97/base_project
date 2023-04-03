package com.pacom.baseproject.network

import com.pacom.baseproject.mode.reponse.TestResponse
import com.pacom.baseproject.network.api.ApiDataHelper
import com.pacom.baseproject.network.header.ApiHeader
import com.pacom.baseproject.network.pref.PrefHelper
import java.util.concurrent.Flow

interface AppDataHelper : ApiDataHelper, PrefHelper {

}