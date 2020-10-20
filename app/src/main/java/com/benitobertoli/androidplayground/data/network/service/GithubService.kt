package com.benitobertoli.androidplayground.data.network.service

import com.benitobertoli.androidplayground.data.network.dto.RepoSearchResponse
import com.benitobertoli.androidplayground.core.SimpleResult
import io.reactivex.Single

interface GithubService {
    fun searchRepositories(
        page: Int? = 1,
        perPage: Int? = 30
    ): Single<SimpleResult<RepoSearchResponse>>
}