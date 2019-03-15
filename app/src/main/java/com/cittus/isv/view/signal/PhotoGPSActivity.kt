package com.cittus.isv.view.signal

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ToggleButton
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.complements.camera.TakePicture
import com.cittus.isv.complements.gps.GPS_Best
import com.cittus.isv.model.*
import kotlinx.android.synthetic.main.activity_photo_gps.view.*

class PhotoGPSActivity : Fragment() {

    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    private var signalArrayList = ArrayList<CittusISV>()
    // Variables Class

    // Cameras
    lateinit var takePictureFront: TakePicture
    lateinit var takePictureBack: TakePicture
    lateinit var takePicturePlaque: TakePicture


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_photo_gps, container, false)
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

        // Btn Camera
        cameraActions()
        // Btn GPS
        gpsActions()

        // Toggle Button
        toggleButtonAction()

        // Btn Save and Next
        btnSave()
    }

    var main = false
    private fun toggleButtonAction() {
        var btn_stretch = viewMain.findViewById<ToggleButton>(R.id.btn_stretch)
        var btn_intersection = viewMain.findViewById<ToggleButton>(R.id.btn_intersection)

        btn_intersection.setOnClickListener {
            // Block Other Button
            btn_intersection.setLinkTextColor(android.R.attr.colorPrimary)
            btn_stretch.setLinkTextColor(Color.RED)
            btn_stretch.isClickable = main
            main = !main
        }
        btn_stretch.setOnClickListener {
            // Block Other Button
            btn_stretch.setLinkTextColor(android.R.attr.colorPrimary)
            btn_intersection.setLinkTextColor(Color.RED)
            btn_intersection.isClickable = main
            main = !main
        }
    }

    private fun cameraActions() {
        // Call on Click
        viewMain.findViewById<ImageButton>(R.id.ibtn_front).setOnClickListener {
            // Init Camera
            takePictureFront = TakePicture(this.activity!!, viewMain.ibtn_front, viewMain.cb_front)
            takePictureFront.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_FRONT)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_back).setOnClickListener {
            // Init Camera
            takePictureBack = TakePicture(this.activity!!, viewMain.ibtn_back, viewMain.cb_back)
            takePictureBack.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_BACK)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_plaque).setOnClickListener {
            // Init Camera
            takePicturePlaque = TakePicture(this.activity!!, viewMain.ibtn_plaque, viewMain.cb_plaque)
            takePicturePlaque.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_PLAQUE)
        }
    }

    private fun btnSave() {
        viewMain!!.findViewById<Button>(R.id.btn_next_photo).setOnClickListener { view ->

            // Get Data Temp
            var tempData = getData()
            // 0 -> Latitud
            // 1 -> Longitud
            // 2 -> Altitud
            // 3 -> Img Front
            // 4 -> Img Back
            // 5 -> Img Plaque
            // 6 -> Location Trayecto


            // Set data temp - GPS PHOTO
            var cittusSignal = CittusSignal()
            cittusSignal.latitude = tempData.get(0).toFloat()
            cittusSignal.longitude = tempData.get(1).toFloat()
            cittusSignal.altitude = tempData.get(2).toFloat()
            cittusSignal.photoFront = tempData.get(3)
            cittusSignal.photoBack = tempData.get(4)
            cittusSignal.photoPlaque = tempData.get(5)
            cittusSignal.location = tempData.get(6)


            signalArrayList.get(0).cittusSignal = cittusSignal

            // Make Object Main
            var cittusDB: CittusListSignal =
                CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
            // Show Data
            Log.e("Data-GPS", cittusDB.toString())
            // Set and Send Data Main
            bundle.putParcelable("CittusDB", cittusDB)
            // Start Activity
            Navigation.findNavController(viewMain!!).navigate(R.id.finishSaveActivity, bundle)

        }
    }


    fun getTakePictureMainFront(): TakePicture {
        if (takePictureFront == null)
            takePictureFront = TakePicture(this.activity!!, viewMain.ibtn_front, viewMain.cb_front)
        return takePictureFront
    }

    fun getTakePictureMainBack(): TakePicture {
        if (takePictureBack == null)
            takePictureBack = TakePicture(this.activity!!, viewMain.ibtn_back, viewMain.cb_back)
        return takePictureBack
    }

    fun getTakePictureMainPlaque(): TakePicture {
        if (takePicturePlaque == null)
            takePicturePlaque = TakePicture(this.activity!!, viewMain.ibtn_plaque, viewMain.cb_plaque)
        return takePicturePlaque
    }

    // Get Location Main User
    private var locationMain: GPS_Best? = null

    private fun gpsActions() {
        locationMain = GPS_Best(
            this.activity!!,
            viewMain.txt_latitude,
            viewMain.txt_altitude,
            viewMain.txt_longitude
        )
        // Set Actions
        viewMain.findViewById<ImageButton>(R.id.btn_gps).setOnClickListener {

            locationMain?.toggleBestUpdates(viewMain)

        }
    }

    fun getData(): ArrayList<String> {
        // TODO: ISV DATA MAIN (1)
        // 0 -> Latitud
        // 1 -> Longitud
        // 2 -> Altitude
        // 3 -> Img Front
        // 4 -> Img Back
        // 5 -> Img Plaque
        // 6 -> Location Trayecto

        var tempArray: ArrayList<String> = ArrayList<String>()
        // Latitude and Longitude

        tempArray.add(0, viewMain.findViewById<EditText>(R.id.txt_latitude).text.toString()) // 0 -> Latitud
        tempArray.add(1, viewMain.findViewById<EditText>(R.id.txt_longitude).text.toString()) // 1 -> Longitud
        tempArray.add(2, viewMain.findViewById<EditText>(R.id.txt_altitude).text.toString()) // 1 -> Altitude


        // Get Imagen
        if (viewMain.findViewById<ImageButton>(R.id.ibtn_front).isClickable) {
            tempArray.add(3, takePictureFront.getFileUriMain().toString()!!) // 3 -> Img Front
        } else {
            tempArray.add(3, "NONE")
        }

        if (viewMain.findViewById<ImageButton>(R.id.ibtn_back).isClickable) {//&& vertical){
            tempArray.add(4, takePictureBack.getFileUriMain().toString()!!) // 4 -> Img Back
        } else {
            tempArray.add(4, "NONE")
        }

        if (viewMain.findViewById<ImageButton>(R.id.ibtn_plaque).isClickable) {//&& vertical){S
            tempArray.add(5, takePicturePlaque.getFileUriMain().toString()!!) // 5 -> Img Plaque
        } else {
            tempArray.add(5, "NONE")
        }

        var btn_stretch = viewMain.findViewById<ToggleButton>(R.id.btn_stretch)
        var btn_intersection = viewMain.findViewById<ToggleButton>(R.id.btn_intersection)

        // Location in the Trayect
        var btnLocationMain: String = ""
        if (btn_stretch.isChecked) {
            btnLocationMain = btn_stretch.textOn.toString()
        } else if (btn_intersection.isChecked) {
            btnLocationMain = btn_intersection.textOn.toString()
        }
        if (btnLocationMain.isNotEmpty()) {
            tempArray.add(6, btnLocationMain)// 1-1 -> Location Trayecto
        } else {
            throw Exception("Location was not Select")
        }


        return tempArray
    }
}
