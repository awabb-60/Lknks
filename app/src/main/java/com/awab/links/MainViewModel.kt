package com.awab.links

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awab.links.model.Repository
import com.awab.links.utils.QRCodeGen
import com.awab.links.utils.allPermissionsGranted
import com.awab.links.utils.grantPermissions
import com.awab.links.utils.isUnderAPI30
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class MainViewModel : ViewModel() {

    private val TAG = "AppDebug"

    val shortLink = MutableLiveData("")

    val qrCodeBitmap = MutableLiveData<Bitmap?>()

    val toastMessage = MutableLiveData<String?>(null)

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
                return
            }
            toastMessage.value = "failed to create QR code!"
            toastMessage.value = null
        }
        toastMessage.value = "type in some text..."
        toastMessage.value = null
    }

    fun getShareTextIntent(context: Context): Intent? {
        val imageFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
                    + File.separatorChar + "share_image.png"
        )

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

    fun saveCurrentQRCode(context: Context, activity: Activity, imageName:String) {

        // check for tha required permissions
        if (allPermissionsGranted(context)) {

            val imageFile = if (isUnderAPI30())
                    File("/storage/emulated/0/Links/$imageName.png")
                else  {
                    val parentDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    File("${parentDir?.absolutePath}${File.separatorChar}$imageName.png")
                }

            val outputStream = FileOutputStream(imageFile)
            qrCodeBitmap.value?.compress(Bitmap.CompressFormat.PNG, 95, outputStream)
            outputStream.close()
            outputStream.flush()
            Toast.makeText(context, "saved ", Toast.LENGTH_SHORT).show()
        } else {
            grantPermissions(activity)
            Log.d(TAG, "saveCurrentQRCode: ask for permissions")
        }
    }

    fun createAppDir(context: Context, activity: Activity) {
//        checkStoragePermission(context, activity)
//        if(File(Environment.getExternalStorageDirectory().absolutePath + File.separatorChar + "Links").mkdir())
//            Toast.makeText(context, "dir created", Toast.LENGTH_SHORT).show()
//        else
//            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
    }
}