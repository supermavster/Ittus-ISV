package com.cittus.isv.complements

import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import com.cittus.isv.model.ActionsRequest
import java.util.*

class Permissions(private var context: FragmentActivity, private var onlyCam: Boolean = false) {

    private var arrayPermissionsCamera = arrayOf(READ_EXTERNAL_STORAGE, CAMERA)
    private var arrayPermissionsGPS = arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
    private var arrayPermissions = arrayOf(READ_EXTERNAL_STORAGE, CAMERA, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
    private val list: ArrayList<String> = ArrayList();


    init {
        if (onlyCam === false) {
            for (permission in arrayPermissionsGPS) {
                list.add(permission)
            }
        }

        for (permission in arrayPermissionsCamera) {
            list.add(permission)
        }
    }

    fun setPermissions(){
        if (!checkPermissions()) requestPermission()
    }
    
    // Camera
    fun checkPermissions(): Boolean {
        if (context != null && list != null) {
            for (permission in list) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    fun requestPermission() {
        if (onlyCam === false) {
            ActivityCompat.requestPermissions(
                context, arrayPermissions,
                ActionsRequest.PERMISSION_REQUEST_CODE
            )
        } else {
            ActivityCompat.requestPermissions(
                context, arrayPermissionsCamera,
                ActionsRequest.PERMISSION_REQUEST_CODE
            )
        }
    }

}