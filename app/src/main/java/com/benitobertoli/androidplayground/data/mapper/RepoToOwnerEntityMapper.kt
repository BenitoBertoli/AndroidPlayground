package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.persistence.entity.OwnerEntity
import com.benitobertoli.androidplayground.domain.model.Repo
import javax.inject.Inject

class RepoToOwnerEntityMapper @Inject constructor() : Mapper<Repo, OwnerEntity> {
    override fun map(input: Repo): OwnerEntity {
        return OwnerEntity(
            input.owner.id,
            input.owner.name,
            input.owner.avatar
        )
    }
}