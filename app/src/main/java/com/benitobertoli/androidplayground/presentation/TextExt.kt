package com.benitobertoli.androidplayground.presentation

import android.view.View
import android.widget.TextView

fun TextView.setTextOrHide(text: String?) {
    if (text.isNullOrEmpty()) this.visibility = View.GONE
    else this.text = text
}