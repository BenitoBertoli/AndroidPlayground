package com.benitobertoli.androidplayground.domain.model

data class Owner(
    val id: Long,
    val name: String,
    val avatar: String?
){
    val smallAvatar: String?
        get() = avatar?.plus("&s=100")
}