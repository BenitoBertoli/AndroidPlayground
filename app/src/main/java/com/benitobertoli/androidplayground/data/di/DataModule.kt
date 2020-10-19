package com.benitobertoli.androidplayground.data.di

import com.benitobertoli.androidplayground.data.network.di.NetworkModule
import dagger.Module

@Module(includes = [NetworkModule::class])
object DataModule