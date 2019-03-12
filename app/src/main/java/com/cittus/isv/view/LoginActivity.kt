package com.cittus.isv.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.model.CittusListSignal
import android.util.Pair as UtilPair


class LoginActivity : Fragment() {

    // Main Variables
    var viewMain:View? = null;
    // Make Bundle
    val bundle = Bundle()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Init View
        viewMain = inflater.inflate(R.layout.activity_login, container, false)

        // Init Process
        initProcess()
        return viewMain
    }

    private fun initProcess(){
        // Btn Login
        btnLogin()
    }

    private fun btnLogin(){
        viewMain!!.findViewById<Button>(R.id.btn_login).setOnClickListener {

            // Add your data from getFactualResults method to bundle
            var cittusDB: CittusListSignal = CittusListSignal(1)
            bundle.putParcelable("CittusDB", cittusDB)
            // Init Action
            Navigation.findNavController(viewMain!!).navigate(R.id.municipalitiesActivity, bundle)
        }
    }

}