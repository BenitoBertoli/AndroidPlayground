package com.benitobertoli.androidplayground.data.persistence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.benitobertoli.androidplayground.data.persistence.RepoContract
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity
import io.reactivex.Maybe

@Dao
interface RepoDao {
    @Insert
    fun insert(repo: RepoEntity): Maybe<Long>

    @Query("SELECT * FROM ${RepoContract.TABLE_NAME} WHERE ${RepoContract.ID} = :id")
    fun findById(id: Long): Maybe<RepoEntity>

    @Query("SELECT * FROM ${RepoContract.TABLE_NAME}")
    fun pagingSource() : PagingSource<Int, RepoEntity>
}