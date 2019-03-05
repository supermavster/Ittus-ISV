package com.cittus.isv

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class TypeSignalActivity: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_type_signal, container, false)

        view.findViewById<Button>(R.id.btn_horizontal).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.locationOnTheWayActivity)
        }

        view.findViewById<Button>(R.id.btn_vertical).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.typeSignalVertical)
        }
        return view
    }
}
