package org.track.fit.services.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import org.track.fit.common.R
import org.track.fit.common.date.toHoursAndMinutes
import org.track.fit.common.exstantion.roundToString
import org.track.fit.data.models.DailyAchievements
import org.track.fit.services.service.PedometerService
import org.track.fit.services.R as MR

object NotificationHelper {

    fun createChanel(context: Context){
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Pedometer",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun createPedometerNotification(context: Context, data:DailyAchievements?): Notification {
        val notificationLayout = RemoteViews(context.packageName, MR.layout.notification_container_view)

        notificationLayout.apply {
            setTextViewText(MR.id.steps_value, data?.steps?.toString() ?: "0")
            val time = data?.duration?.toHoursAndMinutes() ?: 0L.toHoursAndMinutes()
            val duration = "${time.first}${context.getString(MR.string.h)} ${time.second}${context.getString(MR.string.m)}"
            setTextViewText(MR.id.time_value, duration)
            setTextViewText(MR.id.kcal_value, data?.kcal?.roundToString())
        }

        // Stop PedometerService Intent
        val stopIntent = Intent(context, PedometerService::class.java).apply {
            action = PedometerService.ACTION_STOP_PEDOMETER
        }
        val stopPendingIntent = PendingIntent.getService(
            context, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        notificationLayout.setOnClickPendingIntent(MR.id.stop_btn, stopPendingIntent)

        // Launch Main Activity Intent
        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        }

        val launchPendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.footprint_ic)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setPriority(NotificationManager.IMPORTANCE_LOW)
            .setContentIntent(launchPendingIntent)
            .setSound(null)
            .setAutoCancel(false)
            .setOngoing(true)
            .build()
    }

    fun updatePedometerNotification(context: Context, data:DailyAchievements?){
        val notification = createPedometerNotification(context, data)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(PEDOMETER_NOTIFICATION_ID, notification)
    }

    const val NOTIFICATION_CHANNEL_ID  = "notification_channel_id"
    const val PEDOMETER_NOTIFICATION_ID = 2123

}