package com.benitobertoli.androidplayground.presentation.model

import com.benitobertoli.androidplayground.domain.model.Repo

sealed class RepoListState {
    object Loading : RepoListState()
    object Error : RepoListState()
    data class Content(val repositories: List<Repo>) : RepoListState()
}