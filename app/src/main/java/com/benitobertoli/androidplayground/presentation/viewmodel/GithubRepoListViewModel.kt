package com.benitobertoli.androidplayground.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.benitobertoli.androidplayground.presentation.model.RepoListState

interface GithubRepoListViewModel {

    val state: LiveData<RepoListState>

    fun getRepositories()

}