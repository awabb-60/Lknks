package com.awab.links

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awab.links.model.Repository
import com.awab.links.utils.QRCodeGen
import com.awab.links.utils.checkStoragePermission
import com.awab.links.utils.isAPI29AndUp
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class MainViewModel : ViewModel() {

    private val TAG = "AppDebug"

    val shortLink = MutableLiveData("")

    val qrCodeBitmap = MutableLiveData<Bitmap?>()

    private val repository = Repository()

    fun makeShortLink(link: String) = viewModelScope.launch {

        val result: Deferred<String> = async { repository.shortenLink(link) }

        shortLink.value = result.await()
    }

    fun createQRCode(text: String) {
        if (text.isNotBlank()) {
            val bitmap = QRCodeGen().encodeAsBitmap(text, 200, 200)

            if (bitmap != null) {
                qrCodeBitmap.value = bitmap
            }
        }
    }

    fun getShareTextIntent(context: Context): Intent? {
        val imageFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
                    + File.separatorChar + "share_image.png")

        val outputStream = FileOutputStream(imageFile)

        qrCodeBitmap.value?.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
        outputStream.close()
        outputStream.flush()
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, imageFile.toUri())
        }

        return Intent.createChooser(shareIntent, "share...")
    }

    fun saveCurrentQRCode(context: Context, activity: Activity) {
//        checkStoragePermission(context, activity)
//        if (isAPI29AndUp()) {
//            try {
//                val appDir = File(
//                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
//                            + File.separatorChar + "Links"
//                ).apply {
//                    if (mkdir())
//                        Toast.makeText(context, "dir created", Toast.LENGTH_SHORT).show()
//                }
//
//                // saving the image to the storage
//                val imageFile = File(
//                    appDir.absolutePath + File.separatorChar + "share_image.png"
//
//                )
//
//                val outputStream = FileOutputStream(imageFile)
//
//                qrCodeBitmap.value?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                outputStream.close()
//                outputStream.flush()
//                Toast.makeText(context, "saved ", Toast.LENGTH_SHORT).show()
//
//                // creating the share intent
////                val shareIntent = Intent(Intent.ACTION_SEND).apply {
////                    type = "image/png"
////                    putExtra(Intent.EXTRA_STREAM, imageFile.toUri())
////                }
////                val chooser = Intent.createChooser(shareIntent,"share to")
////                chooser
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }

    }

    fun createAppDir(context: Context, activity: Activity) {
        checkStoragePermission(context, activity)

//        if(File(Environment.getExternalStorageDirectory().absolutePath + File.separatorChar + "Links").mkdir())
//            Toast.makeText(context, "dir created", Toast.LENGTH_SHORT).show()
//        else
//            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()

    }
}