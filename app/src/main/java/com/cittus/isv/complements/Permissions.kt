package com.cittus.isv.complements

import android.Manifest
import android.Manifest.permission.*
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import com.cittus.isv.model.ActionsRequest

class Permissions(private var context: FragmentActivity) {

    private var arrayPermissions = arrayOf(READ_EXTERNAL_STORAGE, CAMERA,ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION);

    fun setPermissions(){
        if (!checkPermissions()) requestPermission()
    }
    
    // Camera
    fun checkPermissions(): Boolean {
        if (context != null && arrayPermissions != null) {
            for (permission in arrayPermissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(context, arrayPermissions,
            ActionsRequest.PERMISSION_REQUEST_CODE
        )
    }

}