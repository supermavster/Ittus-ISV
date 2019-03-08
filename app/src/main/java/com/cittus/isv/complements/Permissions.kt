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

    private var arrayPermissionsCamera = arrayOf(READ_EXTERNAL_STORAGE, CAMERA)
    private var arrayPermissionsGPS = arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)

    fun setPermissions(){
        if (!checkPermissions()) requestPermission()
    }
    
    // Camera
    fun checkPermissions(onlyCam: Boolean = false): Boolean {
        if (!onlyCam) {
            arrayPermissionsCamera.plus(arrayPermissionsGPS)
        }

        if (context != null && arrayPermissionsCamera != null) {
            for (permission in arrayPermissionsCamera) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    fun requestPermission(onlyCam: Boolean = false) {
        if (!onlyCam) {
            arrayPermissionsCamera.plus(arrayPermissionsGPS)
        }
        ActivityCompat.requestPermissions(
            context, arrayPermissionsCamera,
            ActionsRequest.PERMISSION_REQUEST_CODE
        )
    }

}