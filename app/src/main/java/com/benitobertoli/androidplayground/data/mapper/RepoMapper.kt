package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.domain.model.Repo
import javax.inject.Inject

class RepoMapper @Inject constructor(): Mapper<RepoDto, Repo> {
    override fun map(input: RepoDto): Repo {
        return Repo(
            input.name,
            input.fullName,
            input.description,
            input.stars,
            input.forks,
            input.language
        )
    }
}