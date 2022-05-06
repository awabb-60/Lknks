package com.awab.links.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.widget.Toast

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