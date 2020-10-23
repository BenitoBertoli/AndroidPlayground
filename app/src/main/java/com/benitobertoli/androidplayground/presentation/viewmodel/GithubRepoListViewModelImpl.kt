package com.benitobertoli.androidplayground.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benitobertoli.androidplayground.core.AppSchedulers
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import com.benitobertoli.androidplayground.presentation.model.RepoListState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GithubRepoListViewModelImpl
@Inject constructor(
    private val githubRepository: GithubRepository,
    private val schedulers: AppSchedulers
) : GithubRepoListViewModel, ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    override val state = MutableLiveData<RepoListState>()

    override fun getRepositories() {
        githubRepository.getRepositories()
            .subscribeOn(schedulers.backgroundScheduler)
            .observeOn(schedulers.foregroundScheduler)
            .doOnSubscribe { state.postValue(RepoListState.Loading) }
            .subscribeBy(
                onSuccess = {
                    it.fold(
                        success = { repos ->
                            state.postValue(RepoListState.Content(repos))
                        },
                        failure = {
                            state.postValue(RepoListState.Error)
                        }
                    )
                },
                onError = {
                    state.postValue(RepoListState.Error)
                }
            ).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}