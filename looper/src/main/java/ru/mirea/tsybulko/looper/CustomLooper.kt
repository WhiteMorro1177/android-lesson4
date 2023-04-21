package ru.mirea.tsybulko.looper

import android.os.Bundle
import android.os.Looper
import android.os.Message
import android.util.Log
import android.os.Handler


class CustomLooper(private val mainThreadHandler: Handler) : Thread() {
    lateinit var handler: Handler
    private val currentClassName: String = CustomLooper::class.java.name

    override fun run() {
        Log.d(currentClassName, "\"run\" method started")
        Looper.prepare()
        handler = Handler(Looper.myLooper()!!) {
            val data: String = it.data.getString("KEY").toString()
            Log.d(currentClassName, "get message: $data")
            mainThreadHandler.sendMessage(
                Message().apply {
                    it.data = Bundle().apply {
                        it.data.putString(
                            "result",
                            data
                        )
                    }
                })
        }
        Looper.loop()
    }
}