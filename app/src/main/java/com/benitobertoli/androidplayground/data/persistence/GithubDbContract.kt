package com.benitobertoli.androidplayground.data.persistence

object RepoContract {
    const val TABLE_NAME = "repositories"
    const val ID = "id"
    const val NAME = "name"
    const val FULL_NAME = "full_name"
    const val DESCRIPTION = "description"
    const val STARS = "stars"
    const val FORKS = "forks"
    const val LANGUAGE = "language"
    const val HOMEPAGE = "homepage"
    const val OWNER_ID = "owner_id"
}

object OwnerContract {
    const val TABLE_NAME = "owners"
    const val ID = "id"
    const val NAME = "name"
    const val AVATAR = "avatar"
}