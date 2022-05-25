package com.benitobertoli.androidplayground.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benitobertoli.androidplayground.data.persistence.dao.RepoDao
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity

@Database(entities = [RepoEntity::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}