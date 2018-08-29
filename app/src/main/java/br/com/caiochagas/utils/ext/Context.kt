package br.com.caiochagas.utils.ext

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context

fun Context.getClipboardManager() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

fun Context.getNotificationManager() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun Context.getAlarmManager() = getSystemService(Context.ALARM_SERVICE) as AlarmManager