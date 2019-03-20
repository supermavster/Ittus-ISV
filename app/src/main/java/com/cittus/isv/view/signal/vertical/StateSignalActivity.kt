package com.cittus.isv.view.signal.vertical

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.model.*

class StateSignalActivity: Fragment() {


    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    private var signalArrayList = ArrayList<CittusISV>()

    // Variable Class
    var verticalSignal: VerticalSignal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_state_signal, container, false)
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
            // Variable Temp
            verticalSignal = checkSignal("Vertical") as VerticalSignal?
            // Init Process
            initProcess()
        }
    }

    private fun checkSignal(type: String): Any? {
        var tempData: Any? = null
        var count = 0
        if (type == "Horizontal") {
            var size = signalArrayList.size
            for (i in 0 until size) {
                var tempObject = signalArrayList[i].horizontalSignal
                if (tempObject != null) {
                    count = i
                }
            }
            tempData = signalArrayList[count].horizontalSignal
        } else if (type == "Vertical") {
            var size = signalArrayList.size
            for (i in 0 until size) {
                var tempObject = signalArrayList[i].verticalSignal
                if (tempObject != null) {
                    count = i
                }
            }
            tempData = signalArrayList[count].verticalSignal
        }
        return tempData
    }

    private fun initProcess() {
        // Set Function - Size
        buttonsSize()
        // Save Data
        btnSave()
    }

    private fun buttonsSize() {
        viewMain.findViewById<ImageButton>(R.id.ibtn_60).setOnClickListener {
            viewMain.findViewById<RadioGroup>(R.id.rg_size).check(R.id.rbtn_60)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_75).setOnClickListener {
            viewMain.findViewById<RadioGroup>(R.id.rg_size).check(R.id.rbtn_75)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_90).setOnClickListener {
            viewMain.findViewById<RadioGroup>(R.id.rg_size).check(R.id.rbtn_90)
        }

        viewMain.findViewById<ImageButton>(R.id.ibtn_120).setOnClickListener {
            viewMain.findViewById<RadioGroup>(R.id.rg_size).check(R.id.rbtn_120)
        }
    }

    private fun btnSave() {
        viewMain.findViewById<Button>(R.id.btn_next_state_signal).setOnClickListener {
            // Get Data
            var data = getData()
            verticalSignal!!.sizeSignal = data[0]
            verticalSignal!!.stateSingal = data[1].toFloat()
            // Make Object Main
            var cittusDB: CittusListSignal =
                CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
            // Show Data
            Log.e("Data-StateS", cittusDB.toString())
            // Set and Send Data Main
            bundle.putParcelable("CittusDB", cittusDB)
            // Start Activity
            Navigation.findNavController(viewMain!!).navigate(R.id.statePostActivity, bundle)
        }
    }


    fun getData(): ArrayList<String> {
        // TODO: Get Data Main - Information
        // 0 -> Size
        // 1 -> Starts - State Post
        var tempArray: ArrayList<String> = ArrayList<String>()
        var radioGroup = viewMain.findViewById<RadioGroup>(R.id.rg_size)
        var rb = viewMain.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        tempArray.add(0, rb.text.toString())

        tempArray.add(1, viewMain.findViewById<RatingBar>(R.id.rb_state_signal).rating.toString())
        return tempArray
    }

}
