package com.benitobertoli.androidplayground.data.network.service

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.benitobertoli.androidplayground.core.AppSchedulers
import com.benitobertoli.androidplayground.core.ListMapper
import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.data.persistence.entity.OwnerEntity
import com.benitobertoli.androidplayground.data.persistence.entity.RemoteKeys
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity
import com.benitobertoli.androidplayground.domain.model.Repo
import io.reactivex.Single
import javax.inject.Inject

private const val GITHUB_FIRST_PAGE = 1
private const val INVALID_PAGE = -1

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator
@Inject
constructor(
    private val githubApi: GithubApi,
    private val repoDtoToRepo: ListMapper<RepoDto, Repo>,
    private val repoToRepoEntity: Mapper<Repo, RepoEntity>,
    private val repoToOwnerEntity: Mapper<Repo, OwnerEntity>,

    private val githubDatabase: GithubDatabase,
    private val appSchedulers: AppSchedulers
) : RxRemoteMediator<Int, Repo>() {

    override fun initializeSingle(): Single<InitializeAction> {
        return Single.just(InitializeAction.LAUNCH_INITIAL_REFRESH)
    }

    override fun loadSingle(loadType: LoadType, state: PagingState<Int, Repo>): Single<MediatorResult> =
        Single.just(loadType)
            .subscribeOn(appSchedulers.backgroundScheduler)
            .flatMap {
                val page = when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextKey?.minus(1) ?: GITHUB_FIRST_PAGE
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                        val prevKey = remoteKeys?.prevKey
                            ?: return@flatMap Single.just(MediatorResult.Success(endOfPaginationReached = remoteKeys != null))
                        prevKey
                    }

                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        val nextKey = remoteKeys?.nextKey
                            ?: return@flatMap Single.just(MediatorResult.Success(endOfPaginationReached = remoteKeys != null))
                        nextKey
                    }
                }

                return@flatMap githubApi.searchRepositories(page, state.config.pageSize)
                    .map { response ->
                        repoDtoToRepo.map(response.items)
                    }.map { repos ->
                        val endOfPaginationReached = repos.isEmpty()
                        githubDatabase.runInTransaction {
                            if (loadType == LoadType.REFRESH) {
                                githubDatabase.remoteKeysDao().clearAll()
                                githubDatabase.ownerDao().clearAll()
                                githubDatabase.repoDao().clearAll()
                            }
                            val prevKey = if (page == GITHUB_FIRST_PAGE) null else page - 1
                            val nextKey = if (endOfPaginationReached) null else page + 1
                            val keys = repos.map { repo ->
                                RemoteKeys(repoId = repo.id, prevKey = prevKey, nextKey = nextKey)
                            }
                            githubDatabase.remoteKeysDao().insertAll(keys)
                            repos.forEach { repo ->
                                githubDatabase.ownerDao().insert(repoToOwnerEntity.map(repo))
                                githubDatabase.repoDao().insert(repoToRepoEntity.map(repo))
                            }
                        }
                        MediatorResult.Success(endOfPaginationReached = endOfPaginationReached) as MediatorResult
                    }.onErrorReturn { error ->
                        MediatorResult.Error(error)
                    }
            }.onErrorReturn {
                MediatorResult.Error(it)
            }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Repo>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                githubDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Repo>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                githubDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Repo>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                githubDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

//    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Repo>): Maybe<RemoteKeys> {
//        val first = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//        first?.let { repo ->
//            return githubDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
//        }
//
//        return Maybe.empty()
//    }
//
//    private fun getRemoteKeyClosestToCurrentPosition(
//        state: PagingState<Int, Repo>
//    ): Maybe<RemoteKeys> {
//        state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { repoId ->
//                return githubDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
//            }
//        }
//        return Maybe.empty()
//    }

}