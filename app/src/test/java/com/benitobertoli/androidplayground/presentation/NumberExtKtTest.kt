package com.benitobertoli.androidplayground.presentation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NumberExtKtTest {

    @Test
    fun `toHumanReadableCount SHOULD return a human readable number`() {
        assertHumanReadable(0L, "0")
        assertHumanReadable(12L, "12")
        assertHumanReadable(999L, "999")
        assertHumanReadable(1_000L, "1k")
        assertHumanReadable(1_100L, "1.1k")
        assertHumanReadable(1_02593L, "102.6k")
        assertHumanReadable(2_100_000L, "2.1M")
        assertHumanReadable(2_200_000_000L, "2.2B")
        assertHumanReadable(3_400_000_000_000L, "3.4T")
        assertHumanReadable(4_800_000_000_000_000L, "4.8P")
        assertHumanReadable(6_700_000_000_000_000_000L, "6.7E")
    }

    private fun assertHumanReadable(input: Long, expected: String) {
        assertThat(input.toHumanReadableCount()).isEqualTo(expected)
    }
}