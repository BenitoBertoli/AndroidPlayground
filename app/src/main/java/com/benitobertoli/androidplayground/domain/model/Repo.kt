package com.benitobertoli.androidplayground.domain.model

data class Repo(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val stars: Long,
    val forks: Long,
    val language: String?,
    val owner: Owner
)