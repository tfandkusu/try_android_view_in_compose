package com.tfandkusu.androidview.util

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.tfandkusu.androidview.BuildConfig
import com.tfandkusu.androidview.model.AppInfo
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppInfo.versionName = BuildConfig.VERSION_NAME
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        MobileAds.initialize(this)
    }
}
