package com.pacom.baseproject

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(){

    @Inject
    lateinit var mCalligraphyConfig: CalligraphyConfig
    override fun onCreate() {
        super.onCreate()
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(mCalligraphyConfig))
                .build()
        )
    }
}