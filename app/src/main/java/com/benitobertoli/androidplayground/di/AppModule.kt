package com.benitobertoli.androidplayground.di

import com.benitobertoli.androidplayground.data.di.DataModule
import com.benitobertoli.androidplayground.domain.di.DomainModule
import com.benitobertoli.androidplayground.presentation.di.PresentationModule
import dagger.Module

@Module(includes = [DataModule::class, DomainModule::class, PresentationModule::class])
object AppModule