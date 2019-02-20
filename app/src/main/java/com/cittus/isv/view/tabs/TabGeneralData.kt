package com.cittus.isv.view.tabs

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cittus.isv.R

@SuppressLint("ValidFragment")
class TabGeneralData @SuppressLint("ValidFragment") constructor(mainActivity: Activity) : Fragment() {
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
        viewOfLayout = inflater!!.inflate(R.layout.tab_main, container, false)
        // Init Process (All Actions in the tab MAIN)
        initProcess()
        return viewOfLayout
    }

    // Initialization - Tab Main (Elements and Actions)
    private fun initProcess() {

    }
}