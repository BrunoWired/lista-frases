package br.com.caiochagas.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.content.Intent.ACTION_MY_PACKAGE_REPLACED
import br.com.caiochagas.utils.NotificationHelper

class RescheduleReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (arrayOf(ACTION_BOOT_COMPLETED, ACTION_MY_PACKAGE_REPLACED).contains(intent.action))
            NotificationHelper.reschedule(context)
    }
}
