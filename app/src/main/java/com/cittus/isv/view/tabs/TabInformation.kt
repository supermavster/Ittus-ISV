package com.cittus.isv.view.tabs

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.cittus.isv.R
import com.cittus.isv.model.EndPoints
import kotlinx.android.synthetic.main.activity_horizontal_main.*
import kotlinx.android.synthetic.main.tab_information.*
import kotlinx.android.synthetic.main.tab_information.view.*

@SuppressLint("ValidFragment")
class TabInformation @SuppressLint("ValidFragment") constructor(mainActivity: Activity) : Fragment() {
        // Global Variables
        // Get Main Activity (To show Elements or Call)
        private var mainActivity  = mainActivity
        // View Elements from Fragment
        private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            // Inflate the layout for this fragment
            viewOfLayout = inflater!!.inflate(R.layout.tab_information, container, false)
            // Init Process (All Actions in the tab MAIN)
            initProcess()
            return viewOfLayout
        }

        // Initialization - Tab Main (Elements and Actions)
        private fun initProcess() {
            // Set Function - Location
            toggleButtonAction()
            // Set Function - Size
            buttonsSize()
            // Set Function - Stay
            buttonStay()
        }

    var main = false
    private fun toggleButtonAction(){
        viewOfLayout.btn_left.setOnClickListener {
            // Block Other Button
            viewOfLayout.btn_right.isClickable = main
            main = !main
        }
        viewOfLayout.btn_right.setOnClickListener {
            // Block Other Button
            viewOfLayout.btn_left.isClickable = main
            main = !main
        }
    }

    private  fun buttonsSize(){
        viewOfLayout.ibtn_60.setOnClickListener {
            viewOfLayout.rg_size.check(R.id.rbtn_60)
        }

        viewOfLayout.ibtn_75.setOnClickListener {
            viewOfLayout.rg_size.check(R.id.rbtn_75)
        }

        viewOfLayout.ibtn_90.setOnClickListener {
            viewOfLayout.rg_size.check(R.id.rbtn_90)
        }

        viewOfLayout.ibtn_120.setOnClickListener {
            viewOfLayout.rg_size.check(R.id.rbtn_120)
        }
    }

    private fun buttonStay(){

        viewOfLayout.ibtn_self_signal.setOnClickListener {
            viewOfLayout.rg_signal.check(R.id.rbtn_self_signal)
        }

        viewOfLayout.ibtn_light_signal.setOnClickListener {
            viewOfLayout.rg_signal.check(R.id.rbtn_light_signal)
        }

        viewOfLayout.ibtn_wall_signal.setOnClickListener {
            viewOfLayout.rg_signal.check(R.id.rbtn_wall_signal)
        }

    }

    fun getData():ArrayList<String>{
        var tempArray: ArrayList<String> = ArrayList<String>()
        var btnLocationMain: String = ""
        if (viewOfLayout.btn_left.isChecked) {
            btnLocationMain = viewOfLayout.btn_left.textOn.toString()
        } else if (viewOfLayout.btn_right.isChecked) {
            btnLocationMain = viewOfLayout.btn_right.textOn.toString()
        }

        tempArray.add("btnLocationMain:$btnLocationMain")

        var rb: RadioButton = viewOfLayout.findViewById(viewOfLayout.rg_size.checkedRadioButtonId)
        tempArray.add("Size:" + rb.text)

        rb = viewOfLayout.findViewById(viewOfLayout.rg_signal.checkedRadioButtonId)
        tempArray.add("Signal:" + rb.text)

        tempArray.add("Starts:" + viewOfLayout.ratingBar.getRating())
        return tempArray
    }
}