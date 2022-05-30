package com.benitobertoli.androidplayground.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.benitobertoli.androidplayground.data.persistence.RemoteKeysContract

@Entity(tableName = RemoteKeysContract.TABLE_NAME)
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo(name = RemoteKeysContract.REPO_ID)
    val repoId: Long,
    @ColumnInfo(name = RemoteKeysContract.PREV_KEY)
    val prevKey: Int?,
    @ColumnInfo(name = RemoteKeysContract.NEXT_KEY)
    val nextKey: Int?
)