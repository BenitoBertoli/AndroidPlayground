package com.benitobertoli.androidplayground.di

import com.benitobertoli.androidplayground.core.AppSchedulers
import com.benitobertoli.androidplayground.core.AppSchedulersImpl
import com.benitobertoli.androidplayground.data.di.DataModule
import com.benitobertoli.androidplayground.domain.di.DomainModule
import com.benitobertoli.androidplayground.presentation.di.PresentationModule
import dagger.Binds
import dagger.Module

@Module(includes = [DataModule::class, DomainModule::class, PresentationModule::class])
interface AppModule {
    @Binds
    fun bindAppSchedulers(impl: AppSchedulersImpl): AppSchedulers
}