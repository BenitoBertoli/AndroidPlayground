package com.benitobertoli.androidplayground.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.benitobertoli.androidplayground.databinding.ItemRepositoryBinding
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.presentation.setTextOrHide
import com.benitobertoli.androidplayground.presentation.toHumanReadableCount

class RepoListAdapter(val clickAction: (Repo) -> Unit) : PagingDataAdapter<Repo, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repo = getItem(position) ?: return
        ItemRepositoryBinding.bind(holder.itemView).apply {
            ownerAvatar.setImageURI(repo.owner.smallAvatar)
            ownerName.text = repo.owner.name
            repoName.text = repo.name
            repoDescription.setTextOrHide(repo.description)
            stars.text = repo.stars.toHumanReadableCount()
            language.text = repo.language
        }
        holder.itemView.setOnClickListener { clickAction(repo) }
    }

    class RepoViewHolder(viewBinding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }
}