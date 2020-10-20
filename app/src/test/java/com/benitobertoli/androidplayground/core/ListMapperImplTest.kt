package com.benitobertoli.androidplayground.core

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class ListMapperImplTest {

    private val mapper: Mapper<Any, Any> = mock()
    private val sut = ListMapperImpl(mapper)

    @Test
    fun `map SHOULD return an empty list WHEN the input is empty`() {
        val input = emptyList<Any>()
        val result = sut.map(input)
        assertThat(result).isEmpty()
    }

    @Test
    fun `map SHOULD return mapped items WHEN the input is not empty`() {
        val inputItem1 = mock<Any>()
        val inputItem2 = mock<Any>()
        val input = listOf(inputItem1, inputItem2)
        val outputItem1 = mock<Any>()
        val outputItem2 = mock<Any>()

        whenever(mapper.map(inputItem1)).thenReturn(outputItem1)
        whenever(mapper.map(inputItem2)).thenReturn(outputItem2)

        val result = sut.map(input)

        assertThat(result).isEqualTo(listOf(outputItem1, outputItem2))
    }
}