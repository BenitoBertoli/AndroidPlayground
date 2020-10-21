package com.benitobertoli.androidplayground.presentation

import com.benitobertoli.androidplayground.R
import com.benitobertoli.androidplayground.presentation.viewmodel.GithubRepoListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GithubRepoListFragment : DaggerFragment(R.layout.fragment_github_repo_list) {

    @Inject
    lateinit var repoListViewModel: GithubRepoListViewModel

}