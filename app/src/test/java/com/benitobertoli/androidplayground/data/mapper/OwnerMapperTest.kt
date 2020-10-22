package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.data.network.dto.OwnerDto
import com.google.common.truth.Truth
import org.junit.Test

class OwnerMapperTest {

    private val sut = OwnerMapper()

    @Test
    fun `map SHOULD map all fields`() {
        val owner = OwnerDto(
            123456789L,
            "JetBrains",
            "some avatar"
        )

        val result = sut.map(owner)

        with(result) {
            Truth.assertThat(id).isEqualTo(owner.id)
            Truth.assertThat(name).isEqualTo(owner.name)
            Truth.assertThat(avatar).isEqualTo(owner.avatar)
        }
    }
}