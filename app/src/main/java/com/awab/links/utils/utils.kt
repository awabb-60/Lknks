package com.awab.links.utils

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun saveToClipBoard(activity:Activity,text:String,successMessage:String = "coped"){
    val clip = ClipData.newPlainText("",text)
    val clipBoardManger = activity.getSystemService(Activity.CLIPBOARD_SERVICE) as ClipboardManager
    clipBoardManger.setPrimaryClip(clip)
    Toast.makeText(activity, successMessage, Toast.LENGTH_SHORT).show()
}

fun getShareTextIntent(data:String):Intent{
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, data)
    }
    return Intent.createChooser(shareIntent, "share link")
}



//fun checkStoragePermission(context: Context, activity: Activity) {
//    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 111)
//
//    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//        && !isAPI29AndUp())
//        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)
//
//}