package com.benitobertoli.androidplayground

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.benitobertoli.androidplayground.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().build()
}