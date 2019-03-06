package com.cittus.isv.view.signal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.R

class PhotoGPSActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_photo_gps, container, false)

        view.findViewById<Button>(R.id.btn_next_photo).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.finishSaveActivity)
        }
        return view
    }
}
