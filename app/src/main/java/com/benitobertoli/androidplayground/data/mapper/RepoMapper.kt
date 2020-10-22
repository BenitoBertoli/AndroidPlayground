package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.network.dto.OwnerDto
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.domain.model.Owner
import com.benitobertoli.androidplayground.domain.model.Repo
import javax.inject.Inject

class RepoMapper @Inject constructor(
    private val ownerMapper: Mapper<OwnerDto, Owner>
) : Mapper<RepoDto, Repo> {
    override fun map(input: RepoDto): Repo {
        return Repo(
            input.id,
            input.name,
            input.fullName,
            input.description,
            input.stars,
            input.forks,
            input.language,
            ownerMapper.map(input.owner)
        )
    }
}