package br.com.caiochagas.utils.ext

fun <T> List<T>?.isNullOrEmpty() = this == null || this.isEmpty()