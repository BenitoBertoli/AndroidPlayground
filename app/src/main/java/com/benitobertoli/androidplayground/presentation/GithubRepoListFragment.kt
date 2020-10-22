package com.benitobertoli.androidplayground.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.benitobertoli.androidplayground.databinding.FragmentGithubRepoListBinding
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.presentation.adapter.RepoListAdapter
import com.benitobertoli.androidplayground.presentation.model.RepoListState
import com.benitobertoli.androidplayground.presentation.viewmodel.GithubRepoListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GithubRepoListFragment : DaggerFragment() {

    private lateinit var binding: FragmentGithubRepoListBinding

    @Inject
    lateinit var repoListViewModel: GithubRepoListViewModel

    private val adapter = RepoListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGithubRepoListBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = adapter


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repoListViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                when (it) {
                    is RepoListState.Loading -> {
                        // todo
                    }

                    is RepoListState.Content -> {
                        setRepositories(it.repositories)
                    }

                    is RepoListState.Error -> {
                        // todo
                    }
                }
            }

        })

        repoListViewModel.getRepositories()
    }

    private fun setRepositories(repos: List<Repo>) {
        adapter.setRepositories(repos)
    }
}