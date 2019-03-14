package com.cittus.isv.view.signal.horizontal

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.model.*

class LocationOnTheWayActivity : Fragment() {

    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    var signalArrayList = ArrayList<CittusISV>()

    lateinit var intentImage: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_location_on_the_way, container, false)
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
        makeActionsButtons()
    }

    private fun makeActionsButtons() {

        viewMain!!.findViewById<ImageButton>(R.id.ibtn_stretch).setOnClickListener { view ->
            makeActivityImages(R.string.title_horizontal_stretch, EndPoints.URL_GET_HORIZONTAL_STRETCH)
        }

        viewMain!!.findViewById<Button>(R.id.btn_stretch).setOnClickListener { view ->
            makeActivityImages(R.string.title_horizontal_stretch, EndPoints.URL_GET_HORIZONTAL_STRETCH)
        }

        viewMain!!.findViewById<ImageButton>(R.id.ibtn_intersection).setOnClickListener { view ->
            makeActivityImages(R.string.title_horizontal_intersection, EndPoints.URL_GET_HORIZONTAL_INTERSECTION)
        }

        viewMain!!.findViewById<Button>(R.id.btn_intersection).setOnClickListener { view ->
            makeActivityImages(R.string.title_horizontal_intersection, EndPoints.URL_GET_HORIZONTAL_INTERSECTION)
        }

    }

    private fun makeActivityImages(title: Int, url_img: String, code: Int = 1) {
        var cittusImage =
            CittusImage(resources.getString(title), url_img, code, ActionsRequest.GET_HORIZONTAL_IMAGES_VALUES)
        bundle.putParcelable("CittusImage", cittusImage)
        // Make Object Main
        var cittusDB: CittusListSignal =
            CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
        // Set and Send Data Main
        bundle.putParcelable("CittusDB", cittusDB)
        // Start Activity
        Navigation.findNavController(viewMain!!).navigate(R.id.mainImage, bundle)
    }

    // Camera Action // Return Intend Action
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // Show Action And Values (Send Variables) - Intent
        Log.e("Location on the Way", requestCode.toString() + "->" + resultCode + "->" + data.toString())
    }
}