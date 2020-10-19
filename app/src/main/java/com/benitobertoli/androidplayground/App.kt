package com.benitobertoli.androidplayground

import androidx.multidex.MultiDex
import com.benitobertoli.androidplayground.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().build()
}