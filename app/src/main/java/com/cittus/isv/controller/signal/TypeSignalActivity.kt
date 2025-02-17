package com.cittus.isv.controller.signal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.model.CittusISV
import com.cittus.isv.model.CittusListSignal
import com.cittus.isv.model.GeolocationCardinalImages
import com.cittus.isv.model.Municipalities

class TypeSignalActivity: Fragment() {


    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    private var signalArrayList = ArrayList<CittusISV>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_type_signal, container, false)
        return viewMain
    }

    // TODO: Get Data - Municipalities
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get Data
        val someDataClass: CittusListSignal? = arguments?.getParcelable("CittusDB")
        someDataClass?.let {
            login = it.login
            municipalities = it.municipality
            geolocationCardinalImages = it.geolocationCardinalImages
            signalArrayList = it.signal!!

        }
        if (login === 1) {
            // Init Process
            initProcess()
        }
    }

    private fun initProcess() {
        viewMain.findViewById<Button>(R.id.btn_horizontal).setOnClickListener {
            setData("Horizontal")
            Navigation.findNavController(viewMain).navigate(R.id.locationOnTheWayActivity, bundle)
        }

        viewMain.findViewById<Button>(R.id.btn_vertical).setOnClickListener {
            setData("Vertical")
            Navigation.findNavController(viewMain).navigate(R.id.typeSignalVertical, bundle)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_count).setOnClickListener {
            Toast.makeText(this.context, "Total de señales registradas: ${signalArrayList.size}", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun setData(typeSignal: String) {


        // Add Signal Values
        var signal = CittusISV(signalArrayList.size, typeSignal)
        signalArrayList.add(signal)

        // Make Object Main
        var cittusDB: CittusListSignal =
            CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
        // Show Data
        Log.e("Data-TypeSignal", cittusDB.toString())
        // Set and Send Data Main
        bundle.putParcelable("CittusDB", cittusDB)
    }
}
