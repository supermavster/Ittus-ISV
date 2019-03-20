package com.cittus.isv.controller

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.model.*

class MainImage : Fragment() {

    // Main Variables
    private lateinit var viewMain: View
    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    var signalArrayList = ArrayList<CittusISV>()
    private var size = 0

    // Variables locals - IMG
    private var imagenSelect: CittusImage? = null
    var mainImages = MainImages()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_main_image, container, false)
        return viewMain
    }


    // TODO: Get Data - Municipalities
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val someDataClass: CittusListSignal? = arguments?.getParcelable("CittusDB")
        someDataClass?.let {
            login = it.login
            municipalities = it.municipality
            geolocationCardinalImages = it.geolocationCardinalImages
            signalArrayList = it.signal!!
        }

        // Get data Image
        imagenSelect = arguments?.getParcelable("CittusImage")

        if (login === 1) {
            size = signalArrayList.size - 1
            // Init Process
            initProcess()
        }
    }


    private fun initProcess() {

        var viewPager = viewMain!!.findViewById<ViewPager>(R.id.viewPager)
        var SliderDots = viewMain!!.findViewById<LinearLayout>(R.id.SliderDots)
        var spinCode = viewMain!!.findViewById<Spinner>(R.id.spin_code_images)

        // Start Process
        mainImages.getValuesMain(imagenSelect, context, viewPager, SliderDots, spinCode)

        // Active Select
        viewMain!!.findViewById<LinearLayout>(R.id.codeMain).visibility =
            View.VISIBLE//findViewById(R.id.codeMain).setVisibility(intent.getBooleanExtra("code", true) ? View.VISIBLE : View.GONE);

        // Set Template New Activity
        viewMain!!.findViewById<TextView>(R.id.lbl_title_images).text = imagenSelect!!.title

        saveElements()

    }


    internal var elementsBase = ArrayList<String>()

    private fun saveElements() {
        viewMain!!.findViewById<Button>(R.id.btn_save_image).setOnClickListener {
            // TODO: Get Data Images
            // 0 -> Number
            // 1 -> Code
            // 2 -> Img Select
            if (mainImages.spinner != null) {
                val ss = mainImages.spinner.selectedItem.toString()
                var arrayCodes = mainImages.arrayCodes
                var arrayImages = mainImages.arrayImages
                for (i in arrayCodes.indices) {
                    if (arrayCodes[i] === ss) {
                        elementsBase.add(0, i.toString())
                        elementsBase.add(1, arrayCodes[i])
                        elementsBase.add(2, arrayImages[i])
                    }
                }
                // Array Add Images
                var imagesByCode: ImagenSignalCode = ImagenSignalCode()
                imagesByCode.idImagen = elementsBase.get(0).toInt()
                imagesByCode.codeImagen = elementsBase.get(1)
                imagesByCode.pathImagen = elementsBase.get(2)

                signalArrayList[size].imagesByCode = imagesByCode

                // Make Object Main
                var cittusDB: CittusListSignal =
                    CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
                // Set and Send Data Main
                Log.e("Data-Image", cittusDB.toString())
                bundle.putParcelable("CittusDB", cittusDB)
                Navigation.findNavController(viewMain!!).navigate(R.id.addressLocationOnTheWayActivity, bundle)
            }
        }
    }
}