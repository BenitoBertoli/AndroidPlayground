package com.benitobertoli.androidplayground.data.persistence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benitobertoli.androidplayground.data.persistence.OwnerContract
import com.benitobertoli.androidplayground.data.persistence.RepoContract
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity
import com.benitobertoli.androidplayground.data.persistence.entity.RepoWithOwner
import io.reactivex.Maybe

@Dao
interface RepoDao {

    private companion object {
        const val QUERY_SELECT_REPO_W_OWNER = "SELECT rep.${RepoContract.ID} as repoId, " +
                "rep.${RepoContract.NAME} as repoName, " +
                "rep.${RepoContract.FULL_NAME} as repoFullName, " +
                "rep.${RepoContract.DESCRIPTION} as repoDescription, " +
                "rep.${RepoContract.STARS} as repoStars, " +
                "rep.${RepoContract.FORKS} as repoForks, " +
                "rep.${RepoContract.LANGUAGE} as repoLanguage, " +
                "rep.${RepoContract.HOMEPAGE} as repoHomepage, " +
                "own.${OwnerContract.ID} as ownerId, " +
                "own.${OwnerContract.NAME} as ownerName, " +
                "own.${OwnerContract.AVATAR} as ownerAvatar " +
                "FROM ${RepoContract.TABLE_NAME} rep " +
                "JOIN ${OwnerContract.TABLE_NAME} own ON rep.${RepoContract.OWNER_ID} = " +
                "own.${OwnerContract.ID} "
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: RepoEntity): Long

    @Query(
        QUERY_SELECT_REPO_W_OWNER +
                " WHERE rep.${RepoContract.ID} = :id"
    )
    fun findById(id: Long): Maybe<RepoWithOwner>

    @Query(QUERY_SELECT_REPO_W_OWNER)
    fun pagingSource(): PagingSource<Int, RepoWithOwner>

    @Query("DELETE FROM ${RepoContract.TABLE_NAME}")
    fun clearAll() : Int
}