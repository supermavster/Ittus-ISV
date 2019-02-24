package com.cittus.isv.view.tabs

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.constraint.solver.widgets.WidgetContainer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import com.cittus.isv.R
import kotlinx.android.synthetic.main.tab_general_data.*
import kotlinx.android.synthetic.main.tab_general_data.view.*

@SuppressLint("ValidFragment")
class TabGeneralData @SuppressLint("ValidFragment") constructor(mainActivity: Activity) : Fragment() {
    // Global Variables
    // Get Main Activity (To show Elements or Call)
    private var mainActivity  = mainActivity
    // View Elements from Fragment
    private var tempLocationSignal = ""
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.tab_general_data, container, false)
        // Init Process (All Actions in the tab MAIN)
        initProcess()
        return viewOfLayout
    }

    // Initialization - Tab Main (Elements and Actions)
    private fun initProcess() {

    }

    private fun setSpinnerData(spinner: Spinner, arrayCodes:Array<String>){
        // Application of the Array to the Spinner
        //val spinnerArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayCodes)
        //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

    }

    fun getData():ArrayList<String>{
        // TODO: Get Data Main - General Data
        // 0 -> LocationBetween 1
        // 1 -> LocationBetween 2
        // 2 -> LocationBetween 3
        // 3 -> Starts 2 - State Signal
        var tempArray: ArrayList<String> = ArrayList<String>()
        tempLocationSignal = viewOfLayout.spin_location_between_signal.selectedItem.toString()+ "# "+viewOfLayout.txt_location_between_signal.text.toString()
        tempArray.add(0, tempLocationSignal)

        tempLocationSignal= viewOfLayout.spin_location_and_signal.selectedItem.toString()+"# "+viewOfLayout.txt_location_and_signal.text.toString()
        tempArray.add(1, tempLocationSignal)

        tempLocationSignal = viewOfLayout.spin_location_with_signal.selectedItem.toString()+"# "+viewOfLayout.txt_location_with_signal.text.toString()
        tempArray.add(2, tempLocationSignal)

        tempArray.add(3, viewOfLayout.rb_state_signal.rating.toString())
        return tempArray
    }
}