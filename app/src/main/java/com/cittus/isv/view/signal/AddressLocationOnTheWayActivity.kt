package com.cittus.isv.view.signal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.model.*

class AddressLocationOnTheWayActivity : Fragment() {

    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    private var signalArrayList = ArrayList<CittusISV>()

    // Variables Class
    private var tempLocationSignal = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_address_location_on_the_way, container, false)
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
            // Init Process
            initProcess()
        }
    }

    private fun initProcess() {
        saveAddress()
    }


    private fun getDataMain(): ArrayList<String> {
        // TODO: Get Data Main - General Data
        // 0 -> LocationBetween 1
        // 1 -> LocationBetween 2
        // 2 -> LocationBetween 3
        // 3 -> Starts 2 - State Signal
        var tempArray: ArrayList<String> = ArrayList<String>()

        var spinLocationBetween: Spinner = viewMain!!.findViewById<Spinner>(R.id.spin_location_between_signal)
        var txtLocationBetween: EditText = viewMain!!.findViewById<EditText>(R.id.txt_location_between_signal)

        var spinLocation: Spinner = viewMain!!.findViewById<Spinner>(R.id.spin_location_and_signal)
        var txtLocation: EditText = viewMain!!.findViewById<EditText>(R.id.txt_location_and_signal)

        var spinLocationSignal: Spinner = viewMain!!.findViewById<Spinner>(R.id.spin_location_with_signal)
        var txtLocationSignal: EditText = viewMain!!.findViewById<EditText>(R.id.txt_location_with_signal)


        tempLocationSignal = spinLocationBetween.selectedItem.toString() + " # " + txtLocationBetween.text.toString()
        tempArray.add(0, tempLocationSignal)

        tempLocationSignal = spinLocation.selectedItem.toString() + " # " + txtLocation.text.toString()
        tempArray.add(1, tempLocationSignal)

        tempLocationSignal = spinLocationSignal.selectedItem.toString() + " # " + txtLocationSignal.text.toString()
        tempArray.add(2, tempLocationSignal)

        return tempArray
    }

    private fun saveAddress() {

        viewMain!!.findViewById<Button>(R.id.btn_next_address).setOnClickListener {


            var locationSignal = LocationSignal()
            // Data temp
            var tempData = getDataMain()
            locationSignal.firstAddressSignal = tempData.get(0)
            locationSignal.secondAddressSignal = tempData.get(1)
            locationSignal.thirdAddressSignal = tempData.get(2)

            // Set Data Address
            signalArrayList.get(0).locationSignal = locationSignal

            // Make Object Main
            var cittusDB: CittusListSignal =
                CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
            // Set and Send Data Main
            bundle.putParcelable("CittusDB", cittusDB)
            // Start Activity
            Navigation.findNavController(viewMain!!).navigate(R.id.stateSignalActivity, bundle)
        }
    }

}
