package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.data.network.dto.OwnerDto
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class OwnerDtoToOwnerMapperTest {

    private val sut = OwnerDtoToOwnerMapper()

    @Test
    fun `map SHOULD map all fields`() {
        val owner = OwnerDto(
            123456789L,
            "JetBrains",
            "some avatar"
        )

        val result = sut.map(owner)

        with(result) {
            assertThat(id).isEqualTo(owner.id)
            assertThat(name).isEqualTo(owner.name)
            assertThat(avatar).isEqualTo(owner.avatar)
        }
    }
}