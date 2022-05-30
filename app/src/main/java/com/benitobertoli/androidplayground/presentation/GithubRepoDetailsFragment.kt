package com.benitobertoli.androidplayground.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.benitobertoli.androidplayground.databinding.FragmentRepositoryDetailsBinding

class GithubRepoDetailsFragment : Fragment() {

    private var _binding: FragmentRepositoryDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRepositoryDetailsBinding.inflate(layoutInflater)
        val repository = arguments?.let { args ->
            GithubRepoDetailsFragmentArgs.fromBundle(args).repository
        } ?: throw IllegalStateException("Tried to open GithubRepoDetailsFragment without passing a repo object")

        with(binding) {
            binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            binding.toolbar.title = repository.fullName

            ownerAvatar.setImageURI(repository.owner.smallAvatar)
            ownerName.text = repository.owner.name
            repoName.text = repository.name
            repoDescription.setTextOrHide(repository.description)
            repoHomepage.setTextOrHide(repository.homepage)
            stars.text = repository.stars.toHumanReadableCount()
            forks.text = repository.forks.toHumanReadableCount()

            repoHomepage.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repository.homepage)))
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}