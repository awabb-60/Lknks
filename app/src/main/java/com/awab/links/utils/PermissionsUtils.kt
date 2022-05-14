package com.awab.links.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat

const val PERMISSIONS_REQUEST_CODE = 99

fun allPermissionsGranted(context: Context):Boolean{
    if (isUnderAPI23())
        return true


    if (isUnderAPI30()){
        return ContextCompat.checkSelfPermission(context,android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED }

    // for api 20 AND HIGHER
    return true
//ContextCompat.checkSelfPermission(context,android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
//            PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(context,android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) ==
//    PackageManager.PERMISSION_GRANTED
}


fun grantPermissions(activity: Activity){
    if (!isUnderAPI23()){

     if (isUnderAPI30()){
         val permission =arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
         activity.requestPermissions(permission, PERMISSIONS_REQUEST_CODE)
     }
    }

}

fun isUnderAPI30() = Build.VERSION.SDK_INT < Build.VERSION_CODES.R

fun isUnderAPI23() = Build.VERSION.SDK_INT < Build.VERSION_CODES.M