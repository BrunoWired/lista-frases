package br.com.caiochagas.utils.ext

import android.widget.EditText

var EditText.value: String
    get() = text.toString()
    set(value) = setText(value)