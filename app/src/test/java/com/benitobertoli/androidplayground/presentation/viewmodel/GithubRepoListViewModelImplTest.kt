package com.benitobertoli.androidplayground.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Rule
import org.junit.Test

class GithubRepoListViewModelImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val githubRepository: GithubRepository = mock()

    private val sut = GithubRepoListViewModelImpl(githubRepository)

    @Test
    fun `getRepositories SHOULD post PaginData returned from the repository`() {
        val repos: List<Repo> = mock()
        val expectedData = PagingData.from(repos)
        whenever(githubRepository.getRepositories()).thenReturn(
            Flowable.just(expectedData)
        )

        sut.getRepositories()

        sut.pagingData.test()
            .assertValue(expectedData)
    }
}