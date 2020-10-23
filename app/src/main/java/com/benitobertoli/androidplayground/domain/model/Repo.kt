package com.benitobertoli.androidplayground.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val stars: Long,
    val forks: Long,
    val language: String?,
    val homepage: String?,
    val owner: Owner
) : Parcelable