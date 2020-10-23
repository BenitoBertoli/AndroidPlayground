package com.benitobertoli.androidplayground.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benitobertoli.androidplayground.databinding.ItemRepositoryBinding
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.presentation.toHumanReadableCount

class RepoListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val repositories = mutableListOf<Repo>()

    override fun getItemCount(): Int = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repo = repositories[position]
        ItemRepositoryBinding.bind(holder.itemView).apply {
            ownerAvatar.setImageURI(repo.owner.smallAvatar)
            ownerName.text = repo.owner.name
            repoName.text = repo.name
            repoDescription.text = repo.description
            stars.text = repo.stars.toHumanReadableCount()
            language.text = repo.language
        }
    }

    class RepoViewHolder(viewBinding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    fun setRepositories(repos: List<Repo>) {
        repositories.clear()
        repositories.addAll(repos)
        notifyDataSetChanged()
    }
}