package ru.mirea.tsybulko.workmanager

import UploadWorker
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WorkManager
            .getInstance(this)
            .enqueue(
                OneTimeWorkRequest.Builder(
                    UploadWorker::class.java
                ).apply {
                    setConstraints(
                        Constraints.Builder().apply {
                            setRequiredNetworkType(NetworkType.UNMETERED)
                            setRequiresCharging(true)
                        }.build())
                }.build()
            )
    }
}