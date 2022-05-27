package com.benitobertoli.androidplayground.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benitobertoli.androidplayground.data.persistence.RemoteKeysContract
import com.benitobertoli.androidplayground.data.persistence.entity.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM ${RemoteKeysContract.TABLE_NAME} WHERE ${RemoteKeysContract.REPO_ID} = :repoId")
    fun remoteKeysRepoId(repoId: Long): RemoteKeys?

    @Query("DELETE FROM ${RemoteKeysContract.TABLE_NAME}")
    fun clearAll() : Int
}