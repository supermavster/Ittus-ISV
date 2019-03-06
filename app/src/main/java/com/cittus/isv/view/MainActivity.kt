package com.cittus.isv.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cittus.isv.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //viewMain.findViewById<Button>(R.id.buttonMain).setOnClickListener { Navigation.findNavController(view).navigate(R.id.geolocalizationActivity)}

}