package com.benitobertoli.androidplayground.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class OwnerTest {

    @Test
    fun `getSmallAvatar SHOULD return null WHEN avatar is null`() {
        val owner = Owner(
            123456789L,
            "JetBrains",
            null
        )

        assertThat(owner.smallAvatar).isNull()
    }

    @Test
    fun `getSmallAvatar SHOULD return a small version avatar WHEN avatar is not null`() {
        val owner = Owner(
            123456789L,
            "JetBrains",
            "https://avatars2.githubusercontent.com/u/878437?v=4"
        )

        assertThat(owner.smallAvatar).isEqualTo("https://avatars2.githubusercontent.com/u/878437?v=4&s=100")
    }
}