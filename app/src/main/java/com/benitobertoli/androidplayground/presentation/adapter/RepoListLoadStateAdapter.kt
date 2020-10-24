package com.benitobertoli.androidplayground.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.benitobertoli.androidplayground.databinding.ItemLoadStateBinding

class RepoListLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<RepoListLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), retry
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        ItemLoadStateBinding.bind(holder.itemView).apply {
            loadingProgress.isVisible = loadState is LoadState.Loading
            loadingMessage.isVisible = loadState is LoadState.Loading
            loadingErrorMessage.isVisible = loadState !is LoadState.Loading
            loadingRetryBtn.isVisible = loadState !is LoadState.Loading
        }
    }

    class LoadStateViewHolder(viewBinding: ItemLoadStateBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            viewBinding.loadingRetryBtn.setOnClickListener { retry() }
        }
    }
}