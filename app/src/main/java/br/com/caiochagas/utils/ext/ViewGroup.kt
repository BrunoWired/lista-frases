package br.com.caiochagas.utils.ext

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layout, this, attachToRoot)