package com.benitobertoli.androidplayground.core

interface Mapper<I, O> {
    fun map(input: I): O
}