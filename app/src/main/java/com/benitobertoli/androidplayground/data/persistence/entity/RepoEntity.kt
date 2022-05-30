package com.benitobertoli.androidplayground.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.benitobertoli.androidplayground.data.persistence.RepoContract

@Entity(tableName = RepoContract.TABLE_NAME)
data class RepoEntity(
    @PrimaryKey
    @ColumnInfo(name = RepoContract.ID)
    val id: Long,
    @ColumnInfo(name = RepoContract.NAME)
    val name: String,
    @ColumnInfo(name = RepoContract.FULL_NAME)
    val fullName: String,
    @ColumnInfo(name = RepoContract.DESCRIPTION)
    val description: String?,
    @ColumnInfo(name = RepoContract.STARS)
    val stars: Long,
    @ColumnInfo(name = RepoContract.FORKS)
    val forks: Long,
    @ColumnInfo(name = RepoContract.LANGUAGE)
    val language: String?,
    @ColumnInfo(name = RepoContract.HOMEPAGE)
    val homepage: String?,
    @ColumnInfo(name = RepoContract.OWNER_ID)
    val ownerId: Long,
)