package ru.mirea.tsybulko.serviceapp

import android.Manifest.permission.FOREGROUND_SERVICE
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import ru.mirea.tsybulko.serviceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binder: ActivityMainBinding
    private var isServiceWorking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun OnPlayButtonClicked(view: View) {
        // check permissions
        if ((ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATIONS
            ) == PermissionChecker.PERMISSION_GRANTED) ==
            (ContextCompat.checkSelfPermission(
                this,
                FOREGROUND_SERVICE
            ) == PermissionChecker.PERMISSION_GRANTED)
        ) {
            Log.d(MainActivity::class.java.name, "PERMISSION_GRANTED")
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(POST_NOTIFICATIONS, FOREGROUND_SERVICE), 200)
        }


        // check service state
        if (!isServiceWorking) {
            val serviceIntent = Intent(this, PlayerService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        else {
            stopService(Intent(this, PlayerService::class.java))
        }
    }
}