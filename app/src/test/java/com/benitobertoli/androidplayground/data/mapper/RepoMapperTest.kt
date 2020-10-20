package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RepoMapperTest {

    private val sut = RepoMapper()

    @Test
    fun `map SHOULD map all fields`() {
        val repo = RepoDto(
            "kotlin",
            "JetBrains/kotlin",
            "The Kotlin Programming Language",
            33712,
            4198,
            "Kotlin"
        )

        val result = sut.map(repo)

        with(result){
            assertThat(name).isEqualTo(repo.name)
            assertThat(fullName).isEqualTo(repo.fullName)
            assertThat(description).isEqualTo(repo.description)
            assertThat(stars).isEqualTo(repo.stars)
            assertThat(forks).isEqualTo(repo.forks)
            assertThat(language).isEqualTo(repo.language)
        }
    }
}