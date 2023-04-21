import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class UploadWorker(
    ctx: Context,
    args: WorkerParameters
) : Worker(ctx, args) {

    override fun doWork(): Result {
        val tag = this::class.java.name

        Log.d(tag, "doWork: start")
        try {
            TimeUnit.SECONDS.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(tag, "doWork: end")
        return Result.success()
    }
}