package com.cittus.isv.view.tabs

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.cittus.isv.R
import com.cittus.isv.controller.GetUserLocation
import com.cittus.isv.controller.MainImage
import com.cittus.isv.view.horizontal.ActivityHorizontalMain
import com.cittus.isv.view.vertical.ActivityVerticalMain
import kotlinx.android.synthetic.main.tab_main.*
import kotlinx.android.synthetic.main.tab_main.view.*

class TabMain : Fragment() {

    private lateinit var viewOfLayout: View
    public var locationMain: GetUserLocation? = GetUserLocation();



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
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = viewOfLayout.findViewById(checkedId)
                when(radio.text){
                    "Horizontal"->startActivity(Intent(this@TabMain.context,ActivityHorizontalMain::class.java))
                    "Vertical"->startActivity(Intent(this@TabMain.context, ActivityVerticalMain::class.java))
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