package com.cittus.isv.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
            //TODO(LOAD DATA) - Example
            // Check Login by Boolean - Make method
            var check: Boolean = true
            var login: Int = if (check) 1 else 0

            /*
            val municipalities = Municipalities(2,"holi")

            val signal = ArrayList<CittusISV>()
            var temp = CittusISV("como me le va")


            var signalTemporal = CittusSignal()
            signalTemporal.altitude = 10f
            temp.cittusSignal = signalTemporal
            signal.add(temp)

            temp = CittusISV("Bien bien")
            signal.add(temp)

            temp = CittusISV("Como le fue a su jermu")
            signal.add(temp)

            val geolocationCardinal = ArrayList<GeolocationCardinalImages>()
            var tempGeo = GeolocationCardinalImages(1,"a","b","c","d")
            geolocationCardinal.add(tempGeo)

            var cittusDB: CittusListSignal = CittusListSignal(login,municipalities,signal,geolocationCardinal)*/
            // Check Login
            if (login === 1) {
                // Start Main Object
                // Make Object Main
                var cittusDB: CittusListSignal = CittusListSignal(login, null, null, null)
                // Show Data
                Log.e("Data-Login", cittusDB.toString())
                // Set and Send Data Main
                bundle.putParcelable("CittusDB", cittusDB)
                // Init Action
                Navigation.findNavController(viewMain!!).navigate(R.id.municipalitiesActivity, bundle)
            } else {
                Toast.makeText(this.context, "Error al ingresr", Toast.LENGTH_SHORT).show()
            }
        }
    }


}