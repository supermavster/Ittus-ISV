package com.cittus.isv.view.signal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        }
        if (login === 1) {
            // Init Process
            initProcess()
        }
    }

    private fun initProcess() {
        viewMain.findViewById<Button>(R.id.btn_horizontal).setOnClickListener {
            setData("Horizontal")
            Navigation.findNavController(viewMain).navigate(R.id.locationOnTheWayActivity)
        }

        viewMain.findViewById<Button>(R.id.btn_vertical).setOnClickListener {
            setData("Vertical")
            Navigation.findNavController(viewMain).navigate(R.id.typeSignalVertical)
        }
    }

    private fun setData(typeSignal: String) {

        var signalArrayList = ArrayList<CittusISV>()

        // Add Signal Values
        var signal = CittusISV(typeSignal)
        signalArrayList.add(signal)

        // Make Object Main
        var cittusDB: CittusListSignal =
            CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
        // Set and Send Data Main
        Log.e("Data", cittusDB.toString())
        bundle.putParcelable("CittusDB", cittusDB)
    }
}
