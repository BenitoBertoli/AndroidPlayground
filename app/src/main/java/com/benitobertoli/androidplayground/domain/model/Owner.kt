package com.benitobertoli.androidplayground.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val id: Long,
    val name: String,
    val avatar: String?
) : Parcelable {
    val smallAvatar: String?
        get() = avatar?.plus("&s=100")
}