package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.domain.model.Owner
import com.benitobertoli.androidplayground.domain.model.Repo
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RepoToRepoEntityMapperTest {
    private val sut = RepoToRepoEntityMapper()

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
            assertThat(id).isEqualTo(repo.id)
            assertThat(name).isEqualTo(repo.name)
            assertThat(fullName).isEqualTo(repo.fullName)
            assertThat(description).isEqualTo(repo.description)
            assertThat(stars).isEqualTo(repo.stars)
            assertThat(forks).isEqualTo(repo.forks)
            assertThat(language).isEqualTo(repo.language)
            assertThat(homepage).isEqualTo(repo.homepage)
            assertThat(ownerId).isEqualTo(owner.id)
        }
    }
}