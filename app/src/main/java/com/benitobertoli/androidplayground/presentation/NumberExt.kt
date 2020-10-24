package com.benitobertoli.androidplayground.presentation

import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

fun Long.toHumanReadableCount(): String {
    if (this < 1000) return "" + this
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    val format = DecimalFormat("0.#")
    val value: String = format.format(this / 1000.0.pow(exp.toDouble()))
    return String.format("%s%c", value, "kMBTPE"[exp - 1])
}