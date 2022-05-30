package com.benitobertoli.androidplayground.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.databinding.FragmentGithubRepoListBinding
import com.benitobertoli.androidplayground.presentation.adapter.RepoListAdapter
import com.benitobertoli.androidplayground.presentation.adapter.RepoListLoadStateAdapter
import com.benitobertoli.androidplayground.presentation.viewmodel.GithubRepoListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GithubRepoListFragment : DaggerFragment() {

    private var _binding: FragmentGithubRepoListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var repoListViewModel: GithubRepoListViewModel

    @Inject
    lateinit var githubDatabase: GithubDatabase

    private val adapter = RepoListAdapter { repo ->
        findNavController().navigate(GithubRepoListFragmentDirections.actionGithubRepoListFragmentToGithubRepoDetailsFragment(repo))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGithubRepoListBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = adapter.withLoadStateFooter(RepoListLoadStateAdapter { adapter.retry() })
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.retryButton.setOnClickListener { repoListViewModel.getRepositories(refresh = true) }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChanged()
                .collectLatest {
                    handleLoadStates(it)
                }
        }

        // https://github.com/googlecodelabs/android-paging/issues/149
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChanged { old, new ->
                    old.mediator?.prepend?.endOfPaginationReached ==
                            new.mediator?.prepend?.endOfPaginationReached}
                .filter { it.refresh is LoadState.NotLoading && it.prepend.endOfPaginationReached && !it.append.endOfPaginationReached}
                .collect {
                    // todo: don't scroll to top if user has already scrolled
                    binding.recyclerView.scrollToPosition(0)
                }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repoListViewModel.pagingData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitData(lifecycle, it)
            }
        }

        repoListViewModel.getRepositories()

        binding.viewFlipper.displayedChild = INDEX_CONTENT
    }

    private fun handleLoadStates(loadState: CombinedLoadStates) {
        if (adapter.itemCount > 0) {
            binding.viewFlipper.displayedChild = INDEX_CONTENT
            return
        }

        when (loadState.refresh) {
            is LoadState.NotLoading -> binding.viewFlipper.displayedChild = INDEX_CONTENT
            is LoadState.Loading -> binding.viewFlipper.displayedChild = INDEX_LOADING
            is LoadState.Error -> {
                binding.viewFlipper.displayedChild = INDEX_ERROR
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        const val INDEX_LOADING = 0
        const val INDEX_ERROR = 1
        const val INDEX_CONTENT = 2
    }
}