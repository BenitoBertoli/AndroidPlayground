package com.benitobertoli.androidplayground.data.persistence.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.benitobertoli.androidplayground.App
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [PersistenceModule.ProvidesModule::class])
interface PersistenceModule {
    @Module
    object ProvidesModule {

        @Provides
        @Singleton
        fun provideGithubDatabase(app: App): GithubDatabase {
            return Room.databaseBuilder(
                app,
                GithubDatabase::class.java, "github-db"
            ).build()
        }
    }
}