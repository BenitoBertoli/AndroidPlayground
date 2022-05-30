package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity
import com.benitobertoli.androidplayground.domain.model.Repo
import javax.inject.Inject

class RepoToRepoEntityMapper @Inject constructor() : Mapper<Repo, RepoEntity> {
    override fun map(input: Repo): RepoEntity {
        return RepoEntity(
            input.id,
            input.name,
            input.fullName,
            input.description,
            input.stars,
            input.forks,
            input.language,
            input.homepage,
            input.owner.id
        )
    }
}