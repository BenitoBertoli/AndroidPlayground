package com.benitobertoli.androidplayground.data.network.service

import androidx.paging.rxjava2.RxPagingSource
import com.benitobertoli.androidplayground.core.AppSchedulers
import com.benitobertoli.androidplayground.core.ListMapper
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.domain.model.Repo
import io.reactivex.Single
import javax.inject.Inject

private const val GITHUB_FIRST_PAGE = 1

class GithubRepoPagingSource
@Inject
constructor(
    private val githubApi: GithubApi,
    private val mapper: ListMapper<RepoDto, Repo>,
    private val appSchedulers: AppSchedulers
) : RxPagingSource<Int, Repo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Repo>> {
        val page = params.key ?: GITHUB_FIRST_PAGE
        return githubApi.searchRepositories(page, params.loadSize)
            .subscribeOn(appSchedulers.backgroundScheduler)
            .observeOn(appSchedulers.foregroundScheduler)
            .map { mapper.map(it.items) }
            .map {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == GITHUB_FIRST_PAGE) null else page - 1,
                    nextKey = if (it.isEmpty()) null else page + 1
                ) as LoadResult<Int, Repo>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}