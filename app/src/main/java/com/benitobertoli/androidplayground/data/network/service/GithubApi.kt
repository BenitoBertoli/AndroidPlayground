package com.benitobertoli.androidplayground.data.network.service

import com.benitobertoli.androidplayground.data.network.dto.RepoSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories?q=kotlin+in:name,description&sort=stars")
    fun searchRepositories(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null
    ): Single<RepoSearchResponse>

}