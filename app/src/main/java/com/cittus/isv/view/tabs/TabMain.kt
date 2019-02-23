package com.cittus.isv.view.tabs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.cittus.isv.R
import com.cittus.isv.complements.Permissions
import com.cittus.isv.complements.camera.TakePicture
import com.cittus.isv.complements.gps.GetUserLocation
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.view.horizontal.ActivityHorizontalMain
import com.cittus.isv.view.vertical.ActivityVerticalMain
import kotlinx.android.synthetic.main.tab_main.*
import kotlinx.android.synthetic.main.tab_main.view.*


@SuppressLint("ValidFragment")
class TabMain @SuppressLint("ValidFragment") constructor(mainActivity: Activity) : Fragment() {
    // Global Variables
    // Get Main Activity (To show Elements or Call)
    private var mainActivity = mainActivity
    var intent: Intent? = null
    // View Elements from Fragment
    private lateinit var viewOfLayout: View
    // Get Location Main User
    private var locationMain: GetUserLocation? = GetUserLocation()

    // Camera
    lateinit var takePicture: TakePicture

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.tab_main, container, false)
        // Init Permissions
        Permissions(mainActivity).setPermissions()
        // Init Process (All Actions in the tab MAIN)
        initProcess()
        return viewOfLayout
    }

    // Initialization - Tab Main (Elements and Actions)
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initProcess() {
        // GPS
        gpsActions()
        // Photos
        cameraActions()
        // Horizontal or Vertical
        radioGroupHVActions()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun gpsActions() {
        // Init Location - Permission Check
        locationMain?.init(mainActivity)
        // Set Actions
        viewOfLayout.btn_gps.setOnClickListener {
            var location = locationMain?.setButtonGPSActions()
            if (location != null) {
                viewOfLayout.txt_latitude.setText(location.longitude.toString())
                viewOfLayout.txt_longitude.setText(location.latitude.toString())
            }
        }
    }

    private fun cameraActions() {
        // Call on Click
        viewOfLayout.ibtn_front.setOnClickListener {
            // Init Camera
            takePicture = TakePicture(mainActivity, viewOfLayout.ibtn_front, viewOfLayout.cb_front)
            takePicture.initProcess()

        }

        viewOfLayout.ibtn_back.setOnClickListener {
            // Init Camera
            takePicture = TakePicture(mainActivity, viewOfLayout.ibtn_back, viewOfLayout.cb_back)
            takePicture.initProcess()

        }

        viewOfLayout.ibtn_plaque.setOnClickListener {
            // Init Camera
            takePicture = TakePicture(mainActivity, viewOfLayout.ibtn_plaque, viewOfLayout.cb_plaque)
            takePicture.initProcess()

        }


    }

    fun getTakePictureMain(): TakePicture {
        return takePicture
    }

    fun processCapturedPhoto(takePicture: TakePicture) {
        takePicture.processCapturedPhoto(takePicture.getPath())
    }

    var photo = false
    private fun radioGroupHVActions() {
        viewOfLayout.radio_group.setOnCheckedChangeListener(
            //Actions Radio Button Main (Information)
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = viewOfLayout.findViewById(checkedId)

                linear_horizontal_one.visibility = View.VISIBLE;
                lbl_photos_signal.visibility = View.VISIBLE;
                when (radio.text) {
                    "Horizontal" -> {
                        cb_front.text = getString(R.string.photo_only)
                        linear_vertical_two.visibility = View.GONE
                        linear_vertical_three.visibility = View.GONE
                    }
                    "Vertical" -> {
                        cb_front.text = getString(R.string.photo_front)
                        linear_vertical_two.visibility = View.VISIBLE
                        linear_vertical_three.visibility = View.VISIBLE
                    }
                }
                // Set Actions
                radio.setOnClickListener {
                    when (radio.text) {
                        "Horizontal" -> {
                            // Init New Activity
                                intent = Intent(mainActivity, ActivityHorizontalMain::class.java)
                        }
                        "Vertical" -> {
                            // Init New Activity
                                intent = Intent(mainActivity, ActivityVerticalMain::class.java)
                        }
                    }
                    if (intent != null) {
                        mainActivity.startActivityForResult(intent, ActionsRequest.GET_HORIZONTAL_VALUES)
                    }

                }
            }
        )

    }


    fun getData(): ArrayList<String> {
        var tempArray: ArrayList<String> = ArrayList<String>()
        // Latitude and Longitude

        tempArray.add("lat:" + viewOfLayout.txt_latitude.text)
        tempArray.add("lon:" + viewOfLayout.txt_longitude.text)

        // Get CLasification
        val radio: RadioButton = viewOfLayout.findViewById(viewOfLayout.radio_group.checkedRadioButtonId)
        tempArray.add("Clarification:" + radio.text)

        // Get Imagen
        var imgFront = viewOfLayout.ibtn_front.drawable
        tempArray.add("imgFront:$imgFront")

        // Get Data - HV
        tempArray.addAll(intent!!.getStringArrayListExtra("getData"))
        return tempArray
    }

}