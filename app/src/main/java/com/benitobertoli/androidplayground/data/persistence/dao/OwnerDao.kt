package com.benitobertoli.androidplayground.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benitobertoli.androidplayground.data.persistence.OwnerContract
import com.benitobertoli.androidplayground.data.persistence.entity.OwnerEntity
import io.reactivex.Maybe

@Dao
interface OwnerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(owner: OwnerEntity): Maybe<Long>

    @Query("DELETE FROM ${OwnerContract.TABLE_NAME}")
    fun clearAll(): Int
}