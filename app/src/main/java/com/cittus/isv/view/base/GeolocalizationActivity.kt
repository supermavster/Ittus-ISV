package com.cittus.isv.view.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.R

class GeolocalizationActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_geolocalization, container, false)

        view.findViewById<Button>(R.id.btn_save_geolocalization).setOnClickListener { Navigation.findNavController(view).navigate(
            R.id.typeSignalActivity
        )}
        return view
    }
}
