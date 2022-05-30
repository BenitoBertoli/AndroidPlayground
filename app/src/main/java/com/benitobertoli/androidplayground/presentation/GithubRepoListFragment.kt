package com.benitobertoli.androidplayground.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.paging.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.databinding.FragmentGithubRepoListBinding
import com.benitobertoli.androidplayground.presentation.adapter.RepoListAdapter
import com.benitobertoli.androidplayground.presentation.adapter.RepoListLoadStateAdapter
import com.benitobertoli.androidplayground.presentation.viewmodel.GithubRepoListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GithubRepoListFragment : DaggerFragment() {

    private lateinit var binding: FragmentGithubRepoListBinding

    @Inject
    lateinit var repoListViewModel: GithubRepoListViewModel

    @Inject
    lateinit var githubDatabase: GithubDatabase

    private val adapter = RepoListAdapter { repo ->
        findNavController().navigate(GithubRepoListFragmentDirections.actionGithubRepoListFragmentToGithubRepoDetailsFragment(repo))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGithubRepoListBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = adapter.withLoadStateFooter(RepoListLoadStateAdapter { adapter.retry() })
        adapter.addLoadStateListener { handleLoadStates(it) }

        binding.retryButton.setOnClickListener { repoListViewModel.getRepositories(refresh = true) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


// sanity check to test if a pagingsource without an intermediary model works
//        val pager = Pager(
//            config = PagingConfig(
//                pageSize = 5,
//                enablePlaceholders = false,
//            ),
//            pagingSourceFactory = { githubDatabase.repoDao().repoPagingSource() }
//        )
//
//        lifecycle.coroutineScope.launch {
//            pager.flow.collectLatest{
//                Log.d("Benito", "data changed $it")
//            }
//        }


        repoListViewModel.pagingData.observe(viewLifecycleOwner) {
            it?.let {
                Log.d("Benito", "new paging data")
                adapter.submitData(lifecycle, it)
            }
        }

        repoListViewModel.getRepositories()

        binding.viewFlipper.displayedChild = INDEX_CONTENT
    }

    private fun handleLoadStates(loadState: CombinedLoadStates) {
        when (loadState.source.refresh) {
            is LoadState.NotLoading -> binding.viewFlipper.displayedChild = INDEX_CONTENT
            is LoadState.Loading -> binding.viewFlipper.displayedChild = INDEX_LOADING
            is LoadState.Error -> binding.viewFlipper.displayedChild = INDEX_ERROR
        }
    }

    private companion object {
        const val INDEX_LOADING = 0
        const val INDEX_ERROR = 1
        const val INDEX_CONTENT = 2
    }
}