package com.benitobertoli.androidplayground.presentation.di

import com.benitobertoli.androidplayground.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface PresentationModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeMainActivity(): MainActivity
}