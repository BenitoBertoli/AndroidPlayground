package com.benitobertoli.androidplayground.data.persistence

object RepoContract {
    const val TABLE_NAME = "repositories"
    const val ID = "repo_id"
    const val NAME = "repo_name"
    const val FULL_NAME = "repo_full_name"
    const val DESCRIPTION = "repo_description"
    const val STARS = "repo_stars"
    const val FORKS = "repo_forks"
    const val LANGUAGE = "repo_language"
    const val HOMEPAGE = "repo_homepage"
    const val OWNER_ID = "repo_owner_id"
}

object OwnerContract {
    const val TABLE_NAME = "owners"
    const val ID = "owner_id"
    const val NAME = "owner_name"
    const val AVATAR = "owner_avatar"
}