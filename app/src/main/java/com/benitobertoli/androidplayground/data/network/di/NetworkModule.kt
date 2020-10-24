package com.benitobertoli.androidplayground.data.network.di

import androidx.paging.PagingSource
import com.benitobertoli.androidplayground.data.network.service.GithubApi
import com.benitobertoli.androidplayground.data.network.service.GithubRepoPagingSource
import com.benitobertoli.androidplayground.data.network.service.GithubService
import com.benitobertoli.androidplayground.data.network.service.GithubServiceImpl
import com.benitobertoli.androidplayground.domain.model.Repo
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule.ProvidesModule::class])
interface NetworkModule {

    @Binds
    fun bindGithubService(impl: GithubServiceImpl): GithubService

    @Binds
    fun bindGithubRepoPagingSource(impl: GithubRepoPagingSource): PagingSource<Int, Repo>

    @Module
    object ProvidesModule {

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideGithubApi(client: OkHttpClient): GithubApi {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubApi::class.java)
        }
    }
}