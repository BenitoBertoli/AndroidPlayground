package com.benitobertoli.androidplayground.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.persistence.entity.RepoWithOwner
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class GithubRepoListViewModelImpl
@Inject constructor(
    private val githubRepository: GithubRepository,
    private val coroutineScope: CoroutineScope? = null,
    private val mapper: Mapper<RepoWithOwner, Repo>
) : GithubRepoListViewModel, ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    override val pagingData = MutableLiveData<PagingData<Repo>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRepositories(refresh: Boolean) {
        if (pagingData.value == null || refresh) {
            githubRepository.getRepositories()
                .map { pagingData ->
                    pagingData.map { repoWithOwner ->
                        mapper.map(repoWithOwner)
                    }
                }
                .cachedIn(coroutineScope ?: viewModelScope)
                .subscribeBy {
                    pagingData.value = it
                }
                .addTo(compositeDisposable)
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}