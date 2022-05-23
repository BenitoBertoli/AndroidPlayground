package com.benitobertoli.androidplayground.data.network.service

import androidx.paging.PagingSource
import com.benitobertoli.androidplayground.core.AppSchedulers
import com.benitobertoli.androidplayground.core.ListMapper
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.data.network.dto.RepoSearchResponse
import com.benitobertoli.androidplayground.domain.model.Repo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

class GithubRepoPagingSourceTest {

    private val githubApi: GithubApi = mock()
    private val mapper: ListMapper<RepoDto, Repo> = mock()
    private val testSchedulers: AppSchedulers = mock {
        whenever(mock.backgroundScheduler).thenReturn(Schedulers.trampoline())
        whenever(mock.foregroundScheduler).thenReturn(Schedulers.trampoline())
    }
    private val sut = GithubRepoPagingSource(githubApi, mapper, testSchedulers)

    @Test
    fun `loadSingle SHOULD return the correct Page params WHEN it is the first page`() {
        val loadParams = PagingSource.LoadParams.Refresh<Int>(null, 30, false)

        val item1 = mock<RepoDto>()
        val item2 = mock<RepoDto>()
        val items = listOf(item1, item2)
        val repo1 = mock<Repo>()
        val repo2 = mock<Repo>()
        val expectedRepos = listOf(repo1, repo2)

        mockRepos(items, expectedRepos)

        sut.loadSingle(loadParams).test()
            .assertValue(
                PagingSource.LoadResult.Page(
                    data = expectedRepos,
                    prevKey = null,
                    nextKey = 2
                )
            )

        verify(githubApi).searchRepositories(1, loadParams.loadSize)
    }

    @Test
    fun `loadSingle SHOULD return the correct Page params WHEN it is not the first page`() {
        val loadParams = PagingSource.LoadParams.Append(3, 30, false)

        val item1 = mock<RepoDto>()
        val item2 = mock<RepoDto>()
        val items = listOf(item1, item2)
        val repo1 = mock<Repo>()
        val repo2 = mock<Repo>()
        val expectedRepos = listOf(repo1, repo2)

        mockRepos(items, expectedRepos)

        sut.loadSingle(loadParams).test()
            .assertValue(
                PagingSource.LoadResult.Page(
                    data = expectedRepos,
                    prevKey = 2,
                    nextKey = 4
                )
            )

        verify(githubApi).searchRepositories(3, loadParams.loadSize)
    }

    @Test
    fun `loadSingle SHOULD return Page with a null nextKey WHEN there are no more items`() {
        val loadParams = PagingSource.LoadParams.Append(2, 30, false)

        mockRepos(emptyList(), emptyList())

        sut.loadSingle(loadParams).test()
            .assertValue(
                PagingSource.LoadResult.Page(
                    data = emptyList(),
                    prevKey = 1,
                    nextKey = null
                )
            )

        verify(githubApi).searchRepositories(2, loadParams.loadSize)
    }

    @Test
    fun `loadSingle SHOULD return Error WHEN the api call fails`() {
        val loadParams = PagingSource.LoadParams.Append(2, 30, false)

        val expectedError: Throwable = mock()
        whenever(githubApi.searchRepositories(anyInt(), anyInt())).thenReturn(
            Single.error(expectedError)
        )

        sut.loadSingle(loadParams).test()
            .assertValue(
                PagingSource.LoadResult.Error(expectedError)
            )
    }

    private fun mockRepos(dtos: List<RepoDto>, repos: List<Repo>) {
        val response = RepoSearchResponse(100, dtos)
        whenever(githubApi.searchRepositories(anyInt(), anyInt())).thenReturn(
            Single.just(response)
        )
        whenever(mapper.map(dtos)).thenReturn(repos)
    }
}