package com.benitobertoli.androidplayground.data.network.dto

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val stars: Long,
    @SerializedName("forks_count") val forks: Long,
    @SerializedName("language") val language: String?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("owner") val owner: OwnerDto
)