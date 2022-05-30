package com.benitobertoli.androidplayground.data.network.service

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.benitobertoli.androidplayground.core.ListMapper
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.data.persistence.entity.RepoWithOwner
import com.benitobertoli.androidplayground.domain.model.Repo
import javax.inject.Inject

class GithubRepoPagingSource
@Inject
constructor(

    private val database: GithubDatabase,
    private val mapper: ListMapper<RepoWithOwner, Repo>
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val loadResult = database.repoDao().pagingSource().load(params)
        Log.d("Benito", loadResult.toString())
        return when (loadResult) {

            is LoadResult.Page ->
                LoadResult.Page(
                    data = mapper.map(loadResult.data),
                    prevKey = loadResult.prevKey,
                    nextKey = loadResult.nextKey
                )
            is LoadResult.Error -> LoadResult.Error(loadResult.throwable)
            is LoadResult.Invalid -> LoadResult.Invalid()
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition
    }
}