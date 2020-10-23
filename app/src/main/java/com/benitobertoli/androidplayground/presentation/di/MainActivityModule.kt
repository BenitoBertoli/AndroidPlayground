package com.benitobertoli.androidplayground.presentation.di

import com.benitobertoli.androidplayground.presentation.GithubRepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @ContributesAndroidInjector(modules = [RepoListFragmentModule::class])
    fun contributeGithubRepoListFragment(): GithubRepoListFragment

}