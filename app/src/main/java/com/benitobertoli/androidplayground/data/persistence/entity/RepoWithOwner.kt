package com.benitobertoli.androidplayground.data.persistence.entity

data class RepoWithOwner(
    val repoId: Long,
    val repoName: String?,
    val repoFullName: String?,
    val repoDescription: String?,
    val repoStars: Long,
    val repoForks: Long,
    val repoLanguage: String?,
    val repoHomepage: String?,
    val ownerId: Long,
    val ownerName: String,
    val ownerAvatar: String?
)