package br.com.caiochagas.utils

import android.app.AlarmManager.INTERVAL_DAY
import android.app.AlarmManager.RTC_WAKEUP
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import br.com.caiochagas.R
import br.com.caiochagas.receiver.AlarmReceiver
import br.com.caiochagas.ui.intro.IntroActivity
import br.com.caiochagas.utils.Constants.CHANNEL_ID
import br.com.caiochagas.utils.Constants.MESSAGE_DAY
import br.com.caiochagas.utils.ext.dayOfYear
import br.com.caiochagas.utils.ext.getAlarmManager
import br.com.caiochagas.utils.ext.getNotificationManager
import br.com.caiochagas.utils.ext.updateHour
import java.util.*


object NotificationHelper {

    fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID, context.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH)

            context.getNotificationManager().createNotificationChannel(channel)
        }
    }

    fun show(context: Context) {

        if (!prefs(context).getBoolean(context.getString(R.string.pref_key_notification), true)) return

        val intent = Intent(context, IntroActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MESSAGE_DAY, true)
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.txt_notification))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_cloud)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(0, builder.build())
    }

    fun reschedule(context: Context) {

        with(prefs(context)) {

            val time = getString(context.getString(R.string.pref_key_notification_time), context.getString(R.string.pref_default_notification_hour))

            schedule(context, time.toInt(), 0)
        }
    }

    private fun schedule(context: Context, hour: Int, minute: Int) {

        val calendar = Calendar.getInstance().apply { updateHour(hour, minute) }

        if (System.currentTimeMillis() > calendar.timeInMillis) calendar.run { dayOfYear += 1 }

        val intent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        context.getAlarmManager().run { setRepeating(RTC_WAKEUP, calendar.timeInMillis, INTERVAL_DAY, pendingIntent) }
    }

    private fun prefs(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
}