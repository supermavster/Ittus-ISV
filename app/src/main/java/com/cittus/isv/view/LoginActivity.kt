package com.cittus.isv.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.R
import android.util.Pair as UtilPair


class LoginActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login, container, false)

        view.findViewById<Button>(R.id.btn_login).setOnClickListener {

            // Make Bundle
            val bundle = Bundle()
            // Add your data from getFactualResults method to bundle
            bundle.putBoolean("isLogin", true)
            // Init Action
            Navigation.findNavController(view).navigate(
                R.id.municipalitiesActivity, bundle
            )

        }
        return view
    }



}