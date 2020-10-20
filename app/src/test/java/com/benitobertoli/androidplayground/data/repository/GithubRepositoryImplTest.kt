package com.benitobertoli.androidplayground.data.repository

import com.benitobertoli.androidplayground.core.AppResult
import com.benitobertoli.androidplayground.core.ListMapper
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.data.network.dto.RepoSearchResponse
import com.benitobertoli.androidplayground.data.network.service.GithubService
import com.benitobertoli.androidplayground.domain.model.Repo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

class GithubRepositoryImplTest {

    private val githubService: GithubService = mock()
    private val repoListMapper: ListMapper<RepoDto, Repo> = mock()

    private val sut = GithubRepositoryImpl(githubService, repoListMapper)

    @Test
    fun `getRepositories SHOULD return list from mapper WHEN service call is successful`() {
        val item1 = mock<RepoDto>()
        val item2 = mock<RepoDto>()
        val items = listOf(item1, item2)
        val response = RepoSearchResponse(100, items)
        val repo1 = mock<Repo>()
        val repo2 = mock<Repo>()
        val expectedRepos = listOf(repo1, repo2)
        whenever(githubService.searchRepositories(anyInt(), anyInt())).thenReturn(
            Single.just(
                AppResult.Success(response)
            )
        )
        whenever(repoListMapper.map(items)).thenReturn(expectedRepos)

        sut.getRepositories()
            .test()
            .assertValue(AppResult.Success(expectedRepos))
    }

}