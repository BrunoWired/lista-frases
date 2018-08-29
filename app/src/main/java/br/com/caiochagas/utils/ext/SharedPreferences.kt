package br.com.caiochagas.utils.ext

import android.content.SharedPreferences

inline fun SharedPreferences.edit(body: SharedPreferences.Editor.() -> SharedPreferences.Editor) = edit().body().apply()