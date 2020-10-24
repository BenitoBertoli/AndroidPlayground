package com.benitobertoli.androidplayground.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.rxjava2.flowable
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GithubRepositoryImpl
@Inject constructor(
    private val githubRepoPagingSource: PagingSource<Int, Repo>
) : GithubRepository {

    companion object {
        private const val PAGE_SIZE = 30
    }

    override fun getRepositories(): Flowable<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { githubRepoPagingSource }
        ).flowable
    }
}