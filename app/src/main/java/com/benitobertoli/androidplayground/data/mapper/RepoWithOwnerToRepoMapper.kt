package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.persistence.entity.RepoWithOwner
import com.benitobertoli.androidplayground.domain.model.Owner
import com.benitobertoli.androidplayground.domain.model.Repo
import javax.inject.Inject

class RepoWithOwnerToRepoMapper @Inject constructor(
) : Mapper<RepoWithOwner, Repo> {
    override fun map(input: RepoWithOwner): Repo {
        return Repo(
            input.repoId,
            input.repoName,
            input.repoFullName,
            input.repoDescription,
            input.repoStars,
            input.repoForks,
            input.repoLanguage,
            input.repoHomepage,
            Owner(
                input.ownerId,
                input.ownerName,
                input.ownerAvatar
            )
        )
    }
}