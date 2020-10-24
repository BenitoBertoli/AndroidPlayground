package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.network.dto.OwnerDto
import com.benitobertoli.androidplayground.domain.model.Owner
import javax.inject.Inject

class OwnerMapper @Inject constructor() : Mapper<OwnerDto, Owner> {
    override fun map(input: OwnerDto): Owner {
        return Owner(
            input.id,
            input.name,
            input.avatar
        )
    }
}