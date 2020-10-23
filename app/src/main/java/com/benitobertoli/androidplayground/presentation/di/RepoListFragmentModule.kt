package com.benitobertoli.androidplayground.presentation.di

import com.benitobertoli.androidplayground.presentation.GithubRepoListFragment
import com.benitobertoli.androidplayground.presentation.viewmodel.GithubRepoListViewModel
import com.benitobertoli.androidplayground.presentation.viewmodel.GithubRepoListViewModelImpl
import com.benitobertoli.androidplayground.presentation.viewmodel.factory.getViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
object RepoListFragmentModule {

    @Provides
    fun provideViewModel(
        viewModelProvider: Provider<GithubRepoListViewModelImpl>,
        fragment: GithubRepoListFragment
    ): GithubRepoListViewModel {
        return fragment.getViewModel { viewModelProvider.get() }
    }

}