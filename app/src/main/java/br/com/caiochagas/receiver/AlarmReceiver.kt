package br.com.caiochagas.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.caiochagas.utils.NotificationHelper

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.show(context)
    }
}