package com.benitobertoli.androidplayground.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.benitobertoli.androidplayground.domain.model.Repo

interface GithubRepoListViewModel {
    val pagingData: LiveData<PagingData<Repo>>

    fun getRepositories(refresh: Boolean = false)
}