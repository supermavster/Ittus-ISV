package com.cittus.isv.complements

import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.cittus.isv.model.ActionsRequest

class Permissions(private var context: Activity) {

    
    @RequiresApi(Build.VERSION_CODES.M)
    public fun setPermissions(){
        checkPermissionsGPS()
        checkPermissionsCamera()
    }
    
    
    // GPS
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissionsGPS(): Boolean {
        // Permission Check
        val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                false
            } else {
                context.requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ), 1
                )
                true
            }
        } else {
        return true
        }
    }
    
    
    // Camera
    public fun checkPermissionsCamera(): Boolean {
        return (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    public fun requestPermission() {
        ActivityCompat.requestPermissions(context, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            ActionsRequest.PERMISSION_REQUEST_CODE

        )
    }

}