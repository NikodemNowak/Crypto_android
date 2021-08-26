package com.nikodem.crypto.ui.alert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color.RED
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_ALL
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nikodem.crypto.R
import com.nikodem.crypto.repositories.CryptoRepository
import com.nikodem.crypto.ui.MainActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CryptoWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params), KoinComponent {

    private val cryptoRepository: CryptoRepository by inject()

    override suspend fun doWork(): Result {
        val cryptoUuid = inputData.getString("COIN_UUID") ?: ""
        val startTargetPrice = inputData.getDouble("COIN_PRICE_START", 0.0)
        val endTargetPrice = inputData.getDouble("COIN_PRICE_END", 0.0)
        val coinName = inputData.getString("COIN_NAME") ?: ""
        val alertName = inputData.getString("ALERT_NAME") ?: ""

        val detailCryptoData = cryptoRepository.getDetailCryptoData(cryptoUuid)
        if (detailCryptoData.data.coin.price.toDouble() > startTargetPrice && detailCryptoData.data.coin.price.toDouble() < endTargetPrice) {
            sendNotification(0, coinName, alertName, startTargetPrice, endTargetPrice)
        }

        return Result.success()
    }

    private fun sendNotification(
        id: Int,
        coinName: String,
        alertName: String,
        startTargetPrice: Double,
        endTargetPrice: Double
    ) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

//        val bitmap = applicationContext.vectorToBitmap(R.drawable.favorite_list_icon)
        val titleNotification = alertName
        val subtitleNotification =
            "Coin $coinName reached value between $startTargetPrice and $endTargetPrice"
        val pendingIntent = getActivity(applicationContext, 0, intent, 0)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.favorite_list_icon)
            .setContentTitle(titleNotification).setContentText(subtitleNotification)
            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)

        notification.priority = PRIORITY_MAX

        if (SDK_INT >= O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_HIGH)

            channel.enableLights(true)
            channel.lightColor = RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }

    companion object {
        const val NOTIFICATION_ID = "appName_notification_id"
        const val NOTIFICATION_NAME = "appName"
        const val NOTIFICATION_CHANNEL = "appName_channel_01"
        const val NOTIFICATION_WORK = "appName_notification_work"
    }
}