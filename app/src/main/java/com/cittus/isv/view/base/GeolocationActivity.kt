package com.cittus.isv.view.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.complements.camera.TakePicture
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.CittusListSignal
import com.cittus.isv.model.GeolocationCardinalImages
import com.cittus.isv.model.Municipalities
import kotlinx.android.synthetic.main.activity_geolocalization.view.*

class GeolocationActivity : Fragment() {

    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null

    // Cameras
    lateinit var takePictureNorth: TakePicture
    lateinit var takePictureSouth: TakePicture
    lateinit var takePictureEast: TakePicture
    lateinit var takePictureWest: TakePicture


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_geolocalization, container, false)
        return viewMain
    }


    // TODO: Get Data - Municipalities
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val someDataClass: CittusListSignal? = arguments?.getParcelable("CittusDB")
        someDataClass?.let {
            login = it.login
            municipalities = it.municipality
        }
        if (login === 1) {
            // Init Process
            initProcess()
        }

    }


    private fun initProcess() {
        // Btn Save and Next
        btnSave()

        // Btn Camera
        cameraGeolocalization()
    }

    private fun cameraGeolocalization() {
        viewMain.findViewById<ImageButton>(R.id.ibtn_north).setOnClickListener {
            // Init Camera
            takePictureNorth = TakePicture(this.activity!!, viewMain.ibtn_north, viewMain.cb_north)
            takePictureNorth.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_NORTH)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_south).setOnClickListener {
            // Init Camera
            takePictureSouth = TakePicture(this.activity!!, viewMain.ibtn_south, viewMain.cb_south)
            takePictureSouth.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_SOUTH)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_west).setOnClickListener {
            // Init Camera
            takePictureWest = TakePicture(this.activity!!, viewMain.ibtn_west, viewMain.cb_west)
            takePictureWest.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_WEST)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_east).setOnClickListener {
            // Init Camera
            takePictureEast = TakePicture(activity!!, viewMain.ibtn_east, viewMain.cb_east)
            takePictureEast.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_EAST)
        }

    }

    fun getTakePictureMainNorth(): TakePicture {
        if (takePictureNorth == null)
            takePictureNorth = TakePicture(this.activity!!, viewMain.ibtn_north, viewMain.cb_north)
        return takePictureNorth
    }

    fun getTakePictureMainSouth(): TakePicture {
        if (takePictureSouth == null)
            takePictureSouth = TakePicture(this.activity!!, viewMain.ibtn_south, viewMain.cb_south)
        return takePictureSouth
    }

    fun getTakePictureMainWest(): TakePicture {
        if (takePictureWest == null)
            takePictureWest = TakePicture(this.activity!!, viewMain.ibtn_west, viewMain.cb_west)
        return takePictureWest
    }

    fun getTakePictureMainEast(): TakePicture {
        if (takePictureEast == null)
            takePictureEast = TakePicture(activity!!, viewMain.ibtn_east, viewMain.cb_east)
        return takePictureEast
    }

    private fun btnSave() {
        viewMain.findViewById<Button>(R.id.btn_save_geolocalization).setOnClickListener {
            var geolocationCardinalImages = ArrayList<GeolocationCardinalImages>()

            var geoImagen = GeolocationCardinalImages("North", getTakePictureMainNorth().getPath()!!)
            geolocationCardinalImages.add(geoImagen)

            geoImagen = GeolocationCardinalImages("South", getTakePictureMainSouth().getPath()!!)
            geolocationCardinalImages.add(geoImagen)

            geoImagen = GeolocationCardinalImages("East", getTakePictureMainEast().getPath()!!)
            geolocationCardinalImages.add(geoImagen)

            geoImagen = GeolocationCardinalImages("West", getTakePictureMainWest().getPath()!!)
            geolocationCardinalImages.add(geoImagen)

            // Make Object Main
            var cittusDB: CittusListSignal = CittusListSignal(login, municipalities, null, geolocationCardinalImages)
            // Set and Send Data Main
            bundle.putParcelable("CittusDB", cittusDB)
            Navigation.findNavController(viewMain!!).navigate(R.id.typeSignalActivity, bundle)

        }
    }


}
