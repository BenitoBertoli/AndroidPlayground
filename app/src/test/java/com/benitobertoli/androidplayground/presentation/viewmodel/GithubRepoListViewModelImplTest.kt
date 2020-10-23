package com.benitobertoli.androidplayground.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.benitobertoli.androidplayground.core.AppResult
import com.benitobertoli.androidplayground.core.AppSchedulers
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import com.benitobertoli.androidplayground.presentation.model.RepoListState
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test

class GithubRepoListViewModelImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val githubRepository: GithubRepository = mock()
    private val testSchedulers: AppSchedulers = mock {
        whenever(mock.backgroundScheduler).thenReturn(Schedulers.trampoline())
        whenever(mock.foregroundScheduler).thenReturn(Schedulers.trampoline())
    }
    private val sut = GithubRepoListViewModelImpl(githubRepository, testSchedulers)

    @Test
    fun `getRepositories SHOULD post Loading`() {
        whenever(githubRepository.getRepositories()).thenReturn(Single.never())

        sut.getRepositories()

        sut.state.test()
            .assertValue(RepoListState.Loading)
    }

    @Test
    fun `getRepositories SHOULD post Error WHEN the repo call returns AppResult Failure`() {
        whenever(githubRepository.getRepositories()).thenReturn(
            Single.just(
                AppResult.Failure(
                    Throwable()
                )
            )
        )

        sut.getRepositories()

        sut.state.test()
            .assertValue(RepoListState.Error)
    }

    @Test
    fun `getRepositories SHOULD post Error WHEN the repo call fails`() {
        whenever(githubRepository.getRepositories()).thenReturn(Single.error(Throwable()))

        sut.getRepositories()

        sut.state.test()
            .assertValue(RepoListState.Error)
    }

    @Test
    fun `getRepositories SHOULD post Content WHEN the repo call returns AppResult Success`() {
        val expectedRepos: List<Repo> = mock()
        whenever(githubRepository.getRepositories()).thenReturn(
            Single.just(AppResult.Success(expectedRepos))
        )

        sut.getRepositories()

        sut.state.test()
            .assertValue(RepoListState.Content(expectedRepos))
    }
}