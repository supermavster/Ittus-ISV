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

class TypeSignalVerticalInformativeActivity : Fragment() {


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
        viewMain = inflater.inflate(R.layout.activity_type_signal_vertical_informative, container, false)
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
        // BTN Services
        btnServices()
        // BTN Touristic
        btnTouristic()
        // BTN Location
        btnLocation()
    }

    private fun btnServices(){
        viewMain.findViewById<ImageButton>(R.id.ibtn_vts_informative_services).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_info_services, EndPoints.URL_GET_VERTICAL_INFO_SERVICES)
        }
        viewMain.findViewById<Button>(R.id.btn_vts_informative_services).setOnClickListener { view ->
            makeActivityImages(R.string.title_vertical_info_services, EndPoints.URL_GET_VERTICAL_INFO_SERVICES)
        }
    }

    private fun btnTouristic(){
        viewMain.findViewById<ImageButton>(R.id.ibtn_vts_informative_turist).setOnClickListener {
            makeActivityImages(R.string.title_vertical_info_turistic, EndPoints.URL_GET_VERTICAL_INFO_TOURIST)
        }
        viewMain.findViewById<Button>(R.id.btn_vts_informative_turist).setOnClickListener {
            makeActivityImages(R.string.title_vertical_info_turistic, EndPoints.URL_GET_VERTICAL_INFO_TOURIST)
        }
    }

    private fun btnLocation(){
        viewMain.findViewById<ImageButton>(R.id.ibtn_vts_informative_location).setOnClickListener {
            makeActivityImages(R.string.title_vertical_info_localization, EndPoints.URL_GET_VERTICAL_INFO_LOCATION)
        }
        viewMain.findViewById<Button>(R.id.btn_vts_informative_location).setOnClickListener {
            makeActivityImages(R.string.title_vertical_info_localization, EndPoints.URL_GET_VERTICAL_INFO_LOCATION)
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
            R.string.title_vertical_info_localization -> "Info. Localizacion"
            R.string.title_vertical_info_services -> "Info. Servicios"
            R.string.title_vertical_info_turistic -> "Info. Turismo"
            else -> ""
        }

        // Name Siganl Vertical
        var verticalSignal = VerticalSignal()
        verticalSignal.verticalNameSignal = verticalNameSignal
        signalArrayList[0].verticalSignal = verticalSignal

        // Make Object Main
        var cittusDB: CittusListSignal =
            CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
        // Show Data
        Log.e("Data-VerticalInfo", cittusDB.toString())
        // Set and Send Data Main
        bundle.putParcelable("CittusDB", cittusDB)
        // Start Activity
        Navigation.findNavController(viewMain).navigate(idSiganl, bundle)
    }

}
