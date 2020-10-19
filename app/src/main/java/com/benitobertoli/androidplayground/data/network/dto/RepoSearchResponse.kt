package com.benitobertoli.androidplayground.data.network.dto

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int,
    @SerializedName("items") val items: List<RepoDto>
)
