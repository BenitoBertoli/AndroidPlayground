package com.benitobertoli.androidplayground.data.network.dto

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val avatar: String?
)