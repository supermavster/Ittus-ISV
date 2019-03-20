package com.cittus.isv.controller.signal.vertical

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.model.*

class TypeSignalVertical : Fragment() {

    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    private var signalArrayList = ArrayList<CittusISV>()

    // Variables Class
    var verticalNameSignal = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_type_signal_vertical, container, false)
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

    private fun initProcess(){
        // BTN Information
        btnInformation()
        // BTN Regulatory
        btnRegulatory()
        // BTN Preventive
        btnPreventive()
        // BTN Work
        btnWork()
        // BTN Cycle Route
        btnCycleRoute()
    }

    private fun btnInformation(){
        viewMain.findViewById<ImageButton>(R.id.ibtn_vertical_informative).setOnClickListener { view ->
            sendData(0, false)
        }
        viewMain.findViewById<Button>(R.id.btn_vertical_informative).setOnClickListener { view ->
            sendData(0, false)
        }
    }

    private fun btnRegulatory(){
        viewMain.findViewById<ImageButton>(R.id.ibtn_vertical_regulatory).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_regulatory, EndPoints.URL_GET_VERTICAL_REGULATORY)
        }
        viewMain.findViewById<Button>(R.id.btn_vertical_regulatory).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_regulatory, EndPoints.URL_GET_VERTICAL_REGULATORY)
        }
    }

    private fun btnPreventive(){
        viewMain.findViewById<ImageButton>(R.id.ibtn_vertical_preventive).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_preventives, EndPoints.URL_GET_VERTICAL_PREVENTIVES)
        }
        viewMain.findViewById<Button>(R.id.btn_vertical_preventive).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_preventives, EndPoints.URL_GET_VERTICAL_PREVENTIVES)
        }
    }

    private fun btnWork(){
        viewMain.findViewById<ImageButton>(R.id.ibtn_vertical_work).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_work, EndPoints.URL_GET_VERTICAL_WORK)
        }
        viewMain.findViewById<Button>(R.id.btn_vertical_work).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_work, EndPoints.URL_GET_VERTICAL_WORK)
        }
    }

    private fun btnCycleRoute(){
        viewMain.findViewById<ImageButton>(R.id.ibtn_vertical_cycle_route).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_cycle_route, EndPoints.URL_GET_VERTICAL_CYCLE_ROUTE)
        }
        viewMain.findViewById<Button>(R.id.btn_vertical_cycle_route).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_cycle_route, EndPoints.URL_GET_VERTICAL_CYCLE_ROUTE)
        }
    }

    private fun makeActivityImages(title: Int, url_img: String, code: Int = 1) {
        var cittusImage =
            CittusImage(resources.getString(title), url_img, code, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        bundle.putParcelable("CittusImage", cittusImage)

        sendData(title)
    }

    private fun sendData(title: Int, idTypeSignal: Boolean = true) {
        var idSiganl = 0
        if (idTypeSignal) {
            idSiganl = R.id.mainImage
        } else {
            idSiganl = R.id.typeSignalVerticalInformativeActivity
        }

        verticalNameSignal = when (title) {
            R.string.title_vertical_regulatory -> "Reglamentaria"
            R.string.title_vertical_preventives -> "Preventiva"
            R.string.title_vertical_work -> "Obra"
            R.string.title_vertical_cycle_route -> "Cicloruta"
            else -> ""
        }

        // Name Siganl Vertical
        var verticalSignal = VerticalSignal()
        verticalSignal.verticalNameSignal = verticalNameSignal
        signalArrayList[signalArrayList.size - 1].verticalSignal = verticalSignal

        // Make Object Main
        var cittusDB: CittusListSignal =
            CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
        // Show Data
        Log.e("Data-Vertical", cittusDB.toString())
        // Set and Send Data Main
        bundle.putParcelable("CittusDB", cittusDB)
        // Start Activity
        Navigation.findNavController(viewMain).navigate(idSiganl, bundle)
    }
}
