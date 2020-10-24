package com.benitobertoli.androidplayground.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi

class GithubRepoListViewModelImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val githubRepository: GithubRepository = mock()
    private val testDispatcher = TestCoroutineDispatcher()
    private val coroutineScope = TestCoroutineScope(testDispatcher)

    private val sut = GithubRepoListViewModelImpl(githubRepository, coroutineScope)

    @Test
    fun `getRepositories SHOULD subscribe WHEN pagingData has no value`() {
        val repos: List<Repo> = mock()
        val expectedData: PagingData<Repo> = PagingData.from(repos)

        whenever(githubRepository.getRepositories()).thenReturn(
            Flowable.just(expectedData)
        )

        sut.getRepositories()

        verify(githubRepository).getRepositories()
    }

    @Test
    fun `getRepositories SHOULD not subscribe WHEN pagingData has a value`() {
        val repos: List<Repo> = mock()
        val paging = PagingData.from(repos)

        sut.pagingData.value = paging

        sut.getRepositories()

        verifyZeroInteractions(githubRepository)
    }
}