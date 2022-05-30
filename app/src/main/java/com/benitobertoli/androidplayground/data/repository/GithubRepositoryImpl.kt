package com.benitobertoli.androidplayground.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.RxRemoteMediator
import androidx.paging.rxjava2.flowable
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.data.persistence.entity.RepoWithOwner
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import io.reactivex.Flowable
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GithubRepositoryImpl
@Inject constructor(
    private val githubDatabase: GithubDatabase,
    private val remoteMediator: RxRemoteMediator<Int, RepoWithOwner>,
) : GithubRepository {

    companion object {
        private const val PAGE_SIZE = 30
    }

    override fun getRepositories(): Flowable<PagingData<RepoWithOwner>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { githubDatabase.repoDao().pagingSource() }
        ).flowable
    }
}