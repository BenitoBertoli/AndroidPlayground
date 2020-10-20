package com.benitobertoli.androidplayground.domain.model

data class Repo(
    val name: String,
    val fullName: String,
    val description: String?,
    val stars: Int,
    val forks: Int,
    val language: String?
)