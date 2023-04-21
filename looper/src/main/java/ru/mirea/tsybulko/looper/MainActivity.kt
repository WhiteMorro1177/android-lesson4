package ru.mirea.tsybulko.looper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.tsybulko.looper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var customLooper: CustomLooper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainThreadHandler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                // Thread.sleep(19)
                Log.d(
                    MainActivity::class.java.simpleName,
                    "Status: ${msg.data.getString("result")}"
                )
            }
        }
        customLooper = CustomLooper(mainThreadHandler)
        customLooper.start()
    }

    fun onClick(view: View) {
        val msg = Message.obtain()

        customLooper.handler.sendMessage(
            msg.apply {
                data = Bundle().apply { putString("KEY", "Student") }
            }
        )
    }
}