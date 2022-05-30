package com.benitobertoli.androidplayground.data.mapper

import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.network.dto.OwnerDto
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.domain.model.Owner
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class RepoDtoToRepoMapperTest {

    private val ownerMapper: Mapper<OwnerDto, Owner> = mock()
    private val sut = RepoDtoToRepoMapper(ownerMapper)

    @Test
    fun `map SHOULD map all fields`() {
        val ownerDto: OwnerDto = mock()
        val expectedOwner: Owner = mock()

        val repo = RepoDto(
            123456789L,
            "kotlin",
            "JetBrains/kotlin",
            "The Kotlin Programming Language",
            33712,
            4198,
            "Kotlin",
            "https://kotlinglang.org",
            ownerDto
        )

        whenever(ownerMapper.map(ownerDto)).thenReturn(expectedOwner)

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
            assertThat(owner).isEqualTo(expectedOwner)
            verify(ownerMapper).map(repo.owner)
        }
    }
}