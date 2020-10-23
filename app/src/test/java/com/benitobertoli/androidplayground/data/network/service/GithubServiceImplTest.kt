package com.benitobertoli.androidplayground.data.network.service

import com.benitobertoli.androidplayground.core.AppResult
import com.benitobertoli.androidplayground.data.network.dto.RepoSearchResponse
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

class GithubServiceImplTest {

    private val githubApi: GithubApi = mock()
    private val sut = GithubServiceImpl(githubApi)

    @Test
    fun `searchRepositories SHOULD return success when the api call is successful`() {

        val expectedResponse = mock<RepoSearchResponse>()
        whenever(githubApi.searchRepositories(anyInt(), anyInt())).thenReturn(
            Single.just(expectedResponse)
        )

        sut.searchRepositories().test()
            .assertValue(AppResult.Success(expectedResponse))
    }

    @Test
    fun `searchRepositories SHOULD return success when the api call fails`() {

        val expectedError = mock<Throwable>()
        whenever(githubApi.searchRepositories(anyInt(), anyInt())).thenReturn(
            Single.error(expectedError)
        )

        sut.searchRepositories().test()
            .assertValue(AppResult.Failure(expectedError))
    }
}