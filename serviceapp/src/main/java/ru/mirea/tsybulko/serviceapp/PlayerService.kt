package ru.mirea.tsybulko.serviceapp

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Notification.PRIORITY_HIGH
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

class PlayerService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder {
        TODO("Forget about it...")
    }

    override fun onCreate() {
        // create notification
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PermissionChecker.PERMISSION_DENIED) return

        val builder = NotificationCompat.Builder(this, "ru.mirea.tsybulko.notificationapp.ANDROID").apply {
            setContentText("Music player started. Now playing: \"От винта\"")
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle("Music")
            priority = PRIORITY_HIGH
        }

        val manager = NotificationManagerCompat.from(this).apply {
            createNotificationChannel(
                NotificationChannel(
                    "ru.mirea.tsybulko.notificationapp.ANDROID",
                    "Music channel",
                    IMPORTANCE_DEFAULT
                )
            )
            notify(1, builder.build())
        }

        // create media player
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // run media player
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { stopForeground(STOP_FOREGROUND_REMOVE) }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        // stop media player
        stopForeground(STOP_FOREGROUND_REMOVE)
        mediaPlayer.stop()
    }
}