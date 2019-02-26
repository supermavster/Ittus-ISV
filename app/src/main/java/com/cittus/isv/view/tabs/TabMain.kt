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
import kotlinx.android.synthetic.main.activity_main.*
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
    lateinit var takePictureFront: TakePicture
    lateinit var takePictureBack: TakePicture
    lateinit var takePicturePlaque: TakePicture

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
            takePictureFront = TakePicture(mainActivity, viewOfLayout.ibtn_front, viewOfLayout.cb_front)
            takePictureFront.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_FRONT)

        }

        viewOfLayout.ibtn_back.setOnClickListener {
            // Init Camera
            takePictureBack = TakePicture(mainActivity, viewOfLayout.ibtn_back, viewOfLayout.cb_back)
            takePictureBack.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_BACK)

        }

        viewOfLayout.ibtn_plaque.setOnClickListener {
            // Init Camera
            takePicturePlaque = TakePicture(mainActivity, viewOfLayout.ibtn_plaque, viewOfLayout.cb_plaque)
            takePicturePlaque.initProcess(ActionsRequest.TAKE_PHOTO_REQUEST_PLAQUE)

        }


    }

    fun getTakePictureMainFront(): TakePicture {
        if(takePictureFront==null){
            takePictureFront = TakePicture(mainActivity, viewOfLayout.ibtn_front, viewOfLayout.cb_front)
        }
        return takePictureFront
    }
    fun getTakePictureMainBack(): TakePicture {
        if(takePictureBack==null){
            takePictureBack = TakePicture(mainActivity, viewOfLayout.ibtn_back, viewOfLayout.cb_back)
        }
        return takePictureBack
    }
    fun getTakePictureMainPlaque(): TakePicture {
        if(takePicturePlaque==null){
            takePicturePlaque = TakePicture(mainActivity, viewOfLayout.ibtn_plaque, viewOfLayout.cb_plaque)
        }
            return takePicturePlaque
    }

    fun processCapturedPhoto(takePicture: TakePicture) {
        takePicture.processCapturedPhoto(takePicture.getPath())
    }

    var horizontal = false
    var vertical = false
    var actionRequest = 0
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
                        //mainActivity.tab_information!!.visibility = View.GONE
                    }
                    "Vertical" -> {
                        cb_front.text = getString(R.string.photo_front)
                        linear_vertical_two.visibility = View.VISIBLE
                        linear_vertical_three.visibility = View.VISIBLE
                        //mainActivity.tab_information!!.visibility = View.VISIBLE
                        //&mainActivity.tabs.addTab(,1);
                        // mainActivity.tabs.removeTab(tabs.getTabAt(3));
                    }
                }
                // Show Data General
                //mainActivity.tab_data_general!!.visibility = View.VISIBLE
                // Set Actions
                radio.setOnClickListener {
                    when (radio.text) {
                        "Horizontal" -> {
                            // Init New Activity
                                if(horizontal===false) {
                                    intent = Intent(mainActivity, ActivityHorizontalMain::class.java)
                                    horizontal = true
                                    vertical = false
                                    actionRequest = ActionsRequest.GET_HORIZONTAL_VALUES
                                }
                        }
                        "Vertical" -> {
                            // Init New Activity
                            if(vertical===false) {
                                intent = Intent(mainActivity, ActivityVerticalMain::class.java)
                                vertical = true
                                horizontal = false
                                actionRequest = ActionsRequest.GET_VERTICAL_IMAGES_VALUES
                            }
                        }
                    }
                    if (intent != null) {
                        mainActivity.startActivityForResult(intent, actionRequest )
                    }

                }
            }
        )

    }


    fun getData(): ArrayList<String> {
        // TODO: ISV DATA MAIN (1)
        // 0 -> Latitud
        // 1 -> Longitud
        // 2 -> Clasification
        // 3 -> Img Front
        // 4 -> Img Back
        // 5 -> Img Plaque
        var tempArray: ArrayList<String> = ArrayList<String>()
        // Latitude and Longitude
        tempArray.add(0, viewOfLayout.txt_latitude.text.toString()) // 0 -> Latitud
        tempArray.add(1, viewOfLayout.txt_longitude.text.toString()) // 1 -> Longitud

        // Get CLasification
        val radio: RadioButton = viewOfLayout.findViewById(viewOfLayout.radio_group.checkedRadioButtonId)
        tempArray.add(2, radio.text.toString()) // 2 -> Clasification

        // Get Imagen
        if(viewOfLayout.ibtn_front.isClickable) {
            tempArray.add(3, takePictureFront.getFileUriMain().toString()!!) // 3 -> Img Front
        }else{
            tempArray.add(3,"NONE")
        }

        if(viewOfLayout.ibtn_back.isClickable && vertical){
            tempArray.add(4,takePictureBack.getFileUriMain().toString()!!) // 4 -> Img Back
        }else{
            tempArray.add(4,"NONE")
        }

        if(viewOfLayout.ibtn_plaque.isClickable && vertical){
            tempArray.add(5, takePicturePlaque.getFileUriMain().toString()!!) // 5 -> Img Plaque
        }else{
            tempArray.add(5,"NONE")
        }

        return tempArray
    }



}