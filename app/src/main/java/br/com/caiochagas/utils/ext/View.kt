package br.com.caiochagas.utils.ext

import android.view.View

fun View.visibleIf(show: Boolean) {

    if (show) show() else hide()
}

fun View.show() {

    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.hide() {

    if (visibility != View.GONE) visibility = View.GONE
}