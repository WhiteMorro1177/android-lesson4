package ru.mirea.tsybulko.thread

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.tsybulko.thread.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counter = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val infoTextView = binding.textView
        val mainThread = Thread.currentThread()
        infoTextView.text = "Current thread name: ${mainThread.name}"

        mainThread.name = "Group number: BSBO-07-21, Serial number: 25, Favorite film: Banlieue 13"
        infoTextView.append("\nNew thread name: ${mainThread.name}")
        Log.d(MainActivity::class.simpleName, "Stack: ${mainThread.stackTrace}")

        binding.button.setOnClickListener {
            Thread {
                val threadNumber = counter++
                Log.d("ThreadProject", "Thread <${threadNumber}> started by student of BSBO-07-21, serial number: 25")

                val endTime = System.currentTimeMillis() + 20 * 1000
                while (System.currentTimeMillis() < endTime) {
                    synchronized(this) {
                        try {
                            Thread.sleep(endTime - System.currentTimeMillis())
                        } catch (e: Exception) {
                            throw RuntimeException(e)
                        }
                    }
                }

                Log.d("ThreadProject", "Thread <${threadNumber}> ended")
            }.start()
        }
    }
}