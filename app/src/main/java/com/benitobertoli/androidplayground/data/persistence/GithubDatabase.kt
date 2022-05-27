package com.benitobertoli.androidplayground.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benitobertoli.androidplayground.data.persistence.dao.OwnerDao
import com.benitobertoli.androidplayground.data.persistence.dao.RemoteKeysDao
import com.benitobertoli.androidplayground.data.persistence.dao.RepoDao
import com.benitobertoli.androidplayground.data.persistence.entity.OwnerEntity
import com.benitobertoli.androidplayground.data.persistence.entity.RemoteKeys
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity

@Database(entities = [RepoEntity::class, OwnerEntity::class, RemoteKeys::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
    abstract fun ownerDao(): OwnerDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}