package com.cittus.isv.view.tabs

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.cittus.isv.R
import com.cittus.isv.controller.GetUserLocation
import com.cittus.isv.view.horizontal.ActivityHorizontalMain
import com.cittus.isv.view.vertical.ActivityVerticalMain
import kotlinx.android.synthetic.main.tab_main.view.*

class TabMain : Fragment() {

    private lateinit var viewOfLayout: View
    public var locationMain: GetUserLocation? = GetUserLocation();

    // Variables Globales Principales
    var classification: String = "";
    var radioButtonClicked: Boolean = true;

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Init Location - Permission Check

        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.tab_main, container, false)




        // Set Actions
        viewOfLayout.radio_group.setOnCheckedChangeListener(

             //Actions Radio Button Main (Information)

            // TODO: Revisar RADIOGROUP
            RadioGroup.OnCheckedChangeListener { group, checkedId ->

                Log.d("Information",group.isClickable.toString())
                Log.d("Information",group.isContextClickable.toString())

                if (radioButtonClicked) {
                    val radio: RadioButton = viewOfLayout.findViewById(checkedId)
                    var intent: Intent? = null
                    classification = radio.text as String
                    when (classification) {
                        "Horizontal" -> {
                            radioButtonClicked = true
                            // Init New Activity
                            intent = Intent(this@TabMain.context, ActivityHorizontalMain::class.java)
                            // Send Variables
                            intent.putExtra("classification", classification)
                        }
                        "Vertical" -> {
                            radioButtonClicked = true
                            intent = Intent(this@TabMain.context, ActivityVerticalMain::class.java)

                        }
                        else->{
                            radioButtonClicked = false
                        }
                    }
                    // Check if Is Null the new Activity and Start the Activity
                    if (intent != null)
                        startActivity(intent)
                }
            }
        )

        viewOfLayout.btn_gps.setOnClickListener {
            var location = locationMain?.setButtonGPSActions()
            if (location != null) {
                viewOfLayout.txt_longitude.setText(location.longitude.toString())
                viewOfLayout.txt_latitude.setText(location.latitude.toString())
            }


        }


        return viewOfLayout
    }


}