package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.domain.model.Owner
import com.benitobertoli.androidplayground.domain.model.Repo
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RepoToOwnerEntityMapperTest {
    private val sut = RepoToOwnerEntityMapper()


    @Test
    fun `map SHOULD map all fields`() {
        val owner = Owner(
            123456789L,
            "JetBrains",
            "some avatar"
        )

        val repo = Repo(
            id = 1,
            name = "name",
            fullName = "full name",
            description = "description",
            stars = 500,
            forks = 10,
            language = "english",
            homepage = null,
            owner = owner
        )

        val result = sut.map(repo)

        with(result) {
            assertThat(id).isEqualTo(owner.id)
            assertThat(name).isEqualTo(owner.name)
            assertThat(avatar).isEqualTo(owner.avatar)
        }
    }
}