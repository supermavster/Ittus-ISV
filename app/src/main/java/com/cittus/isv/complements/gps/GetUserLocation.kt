package com.cittus.isv.complements.gps

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log


class GetUserLocation {

    companion object {
        lateinit var locationManager: LocationManager
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            //txt_longitude.setText(location.longitude.toString())
            //txt_latitude.setText(location.latitude.toString())
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun init(app: Activity) {
        // Create persistent LocationManager reference
        locationManager = (app.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?)!!;


    }

    fun getLocationManager(): LocationManager? {
        return locationManager

    }

    @SuppressLint("MissingPermission")
    fun setButtonGPSActions(): Location? {
        var location:String = "";

        var gps_enabled: Boolean? = null
        var network_enabled: Boolean? = null
        try {


            gps_enabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            // Request location updates

            if(gps_enabled === true) {
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener);
                location = LocationManager.GPS_PROVIDER
            }
            if(network_enabled === true) {
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
                location = LocationManager.NETWORK_PROVIDER
            }



        } catch(ex: SecurityException) {

            Log.d("myTag", "Security Exception, no location available");
        }

        Log.d("myTag", "$network_enabled - $gps_enabled");

        //        locationListener.onLocationChanged(tempLocation)

        return locationManager!!.getLastKnownLocation(location);


    }

}