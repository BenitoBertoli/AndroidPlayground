package com.benitobertoli.androidplayground.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GithubRepoListViewModelImpl
@Inject constructor(
    private val githubRepository: GithubRepository,
    private val coroutineScope: CoroutineScope? = null
) : GithubRepoListViewModel, ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    override val pagingData = MutableLiveData<PagingData<Repo>>()

    override fun getRepositories(refresh: Boolean) {
        if (pagingData.value == null || refresh) {
            githubRepository.getRepositories()
                .cachedIn(coroutineScope ?: viewModelScope)
                .subscribeBy {
                    pagingData.value = it
                }
                .addTo(compositeDisposable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}