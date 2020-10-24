package com.benitobertoli.androidplayground.data.network.service

import com.benitobertoli.androidplayground.data.network.dto.RepoSearchResponse
import com.benitobertoli.androidplayground.core.AppResult
import com.benitobertoli.androidplayground.core.SimpleResult
import io.reactivex.Single
import javax.inject.Inject

class GithubServiceImpl
@Inject constructor(
    private val githubApi: GithubApi
) : GithubService {
    override fun searchRepositories(page: Int?, perPage: Int?): Single<SimpleResult<RepoSearchResponse>> {
        return githubApi.searchRepositories(page, perPage)
            .map { AppResult.Success(it) as SimpleResult<RepoSearchResponse> }
            .onErrorReturn {
                AppResult.Failure(it)
            }
    }
}