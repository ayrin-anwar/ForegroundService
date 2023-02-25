package com.example.foregroundservice
import android.app.*
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat


class ForegroundService: Service() {

    private lateinit var musicplayer:MediaPlayer
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()

        initMusic()
        generateForegroundNotification()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action != null && intent.action.equals(ACTION_STOP,ignoreCase = true)) {
            stopForeground(STOP_FOREGROUND_DETACH)
            stopSelf()
        }
        showNotification()
        musicplayer.start()
        return START_STICKY
    }
    private fun initMusic() {
        musicplayer = MediaPlayer.create(this,R.raw.sound)
        musicplayer.isLooping = true
        musicplayer.setVolume(100F,100F)
    }


    private fun generateForegroundNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChanel = NotificationChannel(notificationId,"MyChannel",IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChanel)
        }
    }
    private fun showNotification() {
        val notificationIntent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, notificationId)
            .setContentTitle("noification")
            .setContentText("notification")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1,notification)

    }
    companion object{
        const val ACTION_STOP="com.example.foregroundservice.STOP"
        const val channelId= 123
        const val notificationId = "com.example.foregroundservice"
    }
}