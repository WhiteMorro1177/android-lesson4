package ru.mirea.tsybulko.cryptoloader

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class CustomLoader(
    ctx: Context,
    private val args: Bundle,
) : AsyncTaskLoader<String>(ctx) {

    private val firstName: String = args.getString(firstNameKey)!!

    companion object {
        const val firstNameKey = "fn_key"
    }

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): String {
        val encodedMessage = args.getByteArray(firstNameKey)
        val encodedKey = args.getByteArray("key")

        return decrypt(encodedMessage, SecretKeySpec(encodedKey, 0, encodedKey!!.size, "AES"))
    }

    @SuppressLint("GetInstance")
    private fun decrypt(encodedMessage: ByteArray?, key: SecretKey): String {
        return try {
            val cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            String(cipher.doFinal(encodedMessage));
        } catch (ex: java.lang.Exception) {
            throw ex
        }
    }

}