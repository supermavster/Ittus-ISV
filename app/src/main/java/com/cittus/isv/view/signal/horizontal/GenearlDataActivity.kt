package com.cittus.isv.view.signal.horizontal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cittus.isv.R

class GenearlDataActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_genearl_data, container, false)

        //view.findViewById<Button>(R.id.btn_login).setOnClickListener {Navigation.findNavController(view).navigate(R.id.municipalitiesActivity)}
        return view
    }
}
