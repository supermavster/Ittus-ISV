package com.cittus.isv

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class StatePostActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_state_post, container, false)

        //view.findViewById<Button>(R.id.btn_login).setOnClickListener {Navigation.findNavController(view).navigate(R.id.municipalitiesActivity)}
        return view
    }
}
