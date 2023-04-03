package com.pacom.baseproject.di

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.pacom.baseproject.R
import com.pacom.baseproject.mode.ApiException
import com.pacom.baseproject.mode.reponse.BaseResponse
import com.pacom.baseproject.network.AppDataHelper
import com.pacom.baseproject.network.AppDataManager
import com.pacom.baseproject.network.api.ApiDataHelper
import com.pacom.baseproject.network.api.ApiDataManager
import com.pacom.baseproject.network.header.ApiHeader
import com.pacom.baseproject.network.header.ProtectedApiHeader
import com.pacom.baseproject.network.header.PublicApiHeader
import com.pacom.baseproject.network.pref.PrefDataManager
import com.pacom.baseproject.network.pref.PrefHelper
import com.pacom.baseproject.utils.ResourceProvider
import com.pacom.baseproject.utils.fromJson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  AppModel {
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext applicationContext: Context) = applicationContext

    @Singleton
    @Provides
    fun providePublicApiHeader() = PublicApiHeader()

    @Provides
    @Singleton
    fun providePrefDataManager(applicationContext: Context, gson: Gson): PrefDataManager = PrefDataManager(applicationContext, gson)


    @Singleton
    @Provides
    fun provideProtectedApiHeader(prefHelper: PrefHelper) = ProtectedApiHeader(prefHelper.getAccessToken())


    @Singleton
    @Provides
    fun provideApiHeader(apiHeader: ApiHeader) = apiHeader

    @Provides
    @Singleton
     fun provideApiDataManager(httpClient: HttpClient): ApiDataManager = ApiDataManager(httpClient)


    @Provides
     fun provideAppDataManager(apiDataManager: ApiDataManager, prefDataManager: PrefDataManager): AppDataManager = AppDataManager(apiDataManager, prefDataManager)


    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("font/sigma/MPLUSRounded1c-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    }


    @Singleton
    fun provideResourceProvider(resourceProvider: ResourceProvider): ResourceProvider {
        return resourceProvider
    }

    @Singleton
    @Provides
    fun provideKtorHttp() = HttpClient(Android){
        expectSuccess = true
        HttpResponseValidator {
            handleResponseException { exception ->
                val clientException = (exception as? ClientRequestException) ?: return@handleResponseException
                val exceptionResponse = exception.response
                if(exceptionResponse.status !in arrayListOf(HttpStatusCode.OK, HttpStatusCode.Accepted, HttpStatusCode.Created)){
                    val responseText = exceptionResponse.readText()
                    val error = Gson().fromJson<BaseResponse<Any>>(responseText)
                    throw ApiException(
                        statusCode = exceptionResponse.status.value,
                        bodyCode = error.status?.code,
                        bodyMessage = error.status?.message,
                        errorDetail = exception.message
                    )
                }
            }
        }


        install(JsonFeature){
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        engine {
            connectTimeout = 60__000
            socketTimeout = 60__000
        }

        install(Logging){
            logger = object: Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}