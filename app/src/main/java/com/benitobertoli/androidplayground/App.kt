package com.benitobertoli.androidplayground

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.benitobertoli.androidplayground.di.DaggerAppComponent
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        initFresco()
    }

    private fun initFresco() {
        val config = ImagePipelineConfig.newBuilder(this)
            .setDownsampleEnabled(true)
            .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
            .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
            .experiment().setNativeCodeDisabled(true)
            .build()
        Fresco.initialize(this, config)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)
}