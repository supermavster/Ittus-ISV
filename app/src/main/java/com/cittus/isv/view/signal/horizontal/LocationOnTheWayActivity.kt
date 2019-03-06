package com.cittus.isv.view.signal.horizontal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.R

class LocationOnTheWayActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_location_on_the_way, container, false)

        view.findViewById<Button>(R.id.btn_intersection).setOnClickListener {
            // Todo: Call Image
            Navigation.findNavController(view).navigate(R.id.mainImage)
        }
        return view
    }


}