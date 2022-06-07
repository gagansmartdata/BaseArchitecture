package com.sdm.mediacard.di

import android.app.Application
import com.sdm.mediacard.BuildConfig
import com.sdm.mediacard.base.timber.ReleaseReportingTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseReportingTree())
        }
    }
}