package abdulmanov.eduard.timetable.presentation.notification

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.data.local.sharedpreferences.FcmSharedPreferences
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation.main.MainActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject
import kotlin.random.Random

class MyFirebaseMessagingService: FirebaseMessagingService() {

    @Inject
    lateinit var fcmSharedPreferences: FcmSharedPreferences

    override fun onCreate() {
        (application as App).appComponent.inject(this)
        super.onCreate()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { sendNotification(it) }
    }

    override fun onNewToken(token: String) {
        fcmSharedPreferences.fcmToken = token
    }

    private fun sendNotification(notification: RemoteMessage.Notification) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.notification_channel_id)
        val channelName = getString(R.string.notification_channel_name)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setColor(ContextCompat.getColor(this, R.color.colorAccent))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle(notification.title)
                    .bigText(notification.body)
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,  channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(Random.nextInt(0, 1000), notificationBuilder.build())
    }
}