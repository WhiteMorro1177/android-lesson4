package ru.mirea.tsybulko.cryptoloader

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.google.android.material.snackbar.Snackbar
import ru.mirea.tsybulko.cryptoloader.databinding.ActivityMainBinding
import java.security.InvalidParameterException
import java.security.SecureRandom
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {
    private lateinit var binding: ActivityMainBinding

    private val loaderID = 1254
    private val tag = localClassName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val encodeResults = Encode(binding.editText.text.toString())

        binding.button.setOnClickListener() {
            LoaderManager.getInstance(this).initLoader(
                loaderID,
                Bundle().also {
                    it.putByteArray(CustomLoader.firstNameKey, encodeResults[1] as ByteArray)
                    it.putByteArray("key", (encodeResults as SecretKey).encoded)
                              }, this
            )
        }
    }

    private fun Encode(toEncode: String): List<Any> {
        val key: SecretKey = generateKey()
        val encodedString: ByteArray = encrypt(toEncode, key)

        return listOf<Any>(key, encodedString)
    }

    private fun generateKey(): SecretKey {
        val secureRandomizer = SecureRandom("random seed for random values".toByteArray())
        val generator: KeyGenerator = KeyGenerator.getInstance("AES").also {
            it.init(256, secureRandomizer)
        }
        return SecretKeySpec(generator.generateKey().encoded, "AES")
    }

    private fun encrypt(target: String, key: SecretKey): ByteArray {
        return try {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            cipher.doFinal(target.toByteArray())
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
        if (id == loaderID) {
            Toast.makeText(this, "Loader with id: $id created", Toast.LENGTH_SHORT).show()
            return CustomLoader(this, args!!)
        }
        throw InvalidParameterException("Invalid loader id")
    }

    override fun onLoaderReset(loader: Loader<String>) {
        Log.d(tag, "call \"onLoaderReset\"")
    }

    override fun onLoadFinished(loader: Loader<String>, data: String?) {
        if (loader.id == loaderID) {
            Snackbar.make(binding.root, "loader \"${loader.id}\" finished, result: $data", Snackbar.LENGTH_SHORT).show()
        }
    }
}