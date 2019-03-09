package com.cittus.isv.view.signal

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
import com.cittus.isv.complements.gps.GPS_Best
import com.cittus.isv.complements.gps.GetUserLocation
import com.cittus.isv.model.ActionsRequest
import kotlinx.android.synthetic.main.activity_photo_gps.view.*

class PhotoGPSActivity : Fragment() {

    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()

    // Cameras
    lateinit var takePictureFront: TakePicture
    lateinit var takePictureBack: TakePicture
    lateinit var takePicturePlaque: TakePicture

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_photo_gps, container, false)
        // Init Process
        initProcess()
        return viewMain
    }

    // TODO: Get Data - Municipalities
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get Values from Login
        val venName = arguments?.getStringArrayList("getData")
        //Log.e("getData",venName.toString())


        /*val someDataClass: SomeDataClass? = arguments?.getParcelable("custom_object")
        someDataClass?.let {
            customObjectField.text = it.someField
            customObjectNumber.text = it.anotherField.toString()
        }
        */
    }


    private fun initProcess() {
        // Btn Save and Next
        btnSave()
        // Btn Camera
        cameraActions()
        // Btn GPS
        gpsActions()
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
        viewMain.findViewById<Button>(R.id.btn_next_photo).setOnClickListener {
            Navigation.findNavController(viewMain).navigate(R.id.finishSaveActivity)
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
    private var locationMain: GetUserLocation? = GetUserLocation()

    private fun gpsActions() {
        // Set Actions
        viewMain.findViewById<ImageButton>(R.id.btn_gps).setOnClickListener {

            /*locationMain?.init(this.activity!!)
            var location = locationMain?.setButtonGPSActions()
            if (location != null) {
                viewMain.txt_latitude.setText(location.longitude.toString())
                viewMain.txt_longitude.setText(location.latitude.toString())
            }*/
            GPS_Best(
                this.activity!!,
                viewMain.txt_latitude,
                viewMain.txt_altitude,
                viewMain.txt_longitude
            )?.toggleBestUpdates(viewMain)

        }
    }
}
