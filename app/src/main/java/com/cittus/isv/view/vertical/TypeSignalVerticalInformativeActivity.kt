package com.cittus.isv.view.vertical

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.controller.MainImage
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.EndPoints
import kotlinx.android.synthetic.main.activity_type_signal_vertical_informative.*
import java.util.*

class TypeSignalVerticalInformativeActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_type_signal_vertical_informative, container, false)

        //Imagen
        view.findViewById<Button>(R.id.btn_vts_informative_services).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.mainImage)
        }
        // Init Process initProcess()
        return view
    }

    /*

    private fun initProcess(){
        // BTN Services
        btnServices()
        // BTN Touristic
        btnTouristic()
        // BTN Location
        btnLocation()
    }

    private fun btnServices(){

        var intent = makeActivityImages(R.string.title_vertical_info_services,EndPoints.URL_GET_VERTICAL_INFO_SERVICES)
        ibtn_vts_informative_services.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
        btn_vts_informative_services.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
    }

    private fun btnTouristic(){

        var intent = makeActivityImages(R.string.title_vertical_info_turistic,EndPoints.URL_GET_VERTICAL_INFO_TOURIST)
        ibtn_vts_informative_turist.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
        btn_vts_informative_turist.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
    }

    private fun btnLocation(){

        var intent = makeActivityImages(R.string.title_vertical_info_localization,EndPoints.URL_GET_VERTICAL_INFO_LOCATION)
        ibtn_vts_informative_location.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
        btn_vts_informative_location.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
    }

    // Add class
    private fun makeActivityImages(title: Int, url_img: String,code:Boolean=true,description: Boolean=false): Intent {
        var intent: Intent = Intent(this, MainImage::class.java)
        Log.i("TEST",url_img)
        intent.putExtra("title", resources.getString(title))
        intent.putExtra("url_img", (url_img))
        intent.putExtra("code",code)
        intent.putExtra("description",description)
        return(intent)
    }

    var exceptionMain: Boolean = false
    private fun getData(): ArrayList<String> {

        return ArrayList<String>()
    }

    // Actions To Return
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i("Vertical Information", requestCode.toString() + "->" + resultCode + "->" + data.toString())
        when (requestCode) {
            ActionsRequest.GET_VERTICAL_IMAGES_VALUES -> {
                // TODO: Get Data Images
                // 0 -> Number
                // 1 -> Code
                // 2 -> Img Select
                if (data != null) {
                    val extras = data!!.extras ?: return
                    val titleTemp = extras.getString("getTitle")
                    val elementsBaseImage = extras.getStringArrayList("getDataImages")
                    Log.e("getData", "getDataImages:$elementsBaseImage")
                    // Get Data of this Activity
                    var data = getData();
                    // Check Errors
                    if (exceptionMain === false) {
                        // Init Process TO send  MAIN ACTIVIVTY
                        var intentTemp: Intent = Intent()
                        intentTemp.putExtra("getTitle", titleTemp)
                        intentTemp.putExtra("getDataImages", elementsBaseImage)
                        setResult(ActionsRequest.GET_VERTICAL_IMAGES_VALUES and ActionsRequest.GET_VERTICAL_VALUES, intentTemp)
                        finish()
                    }
                }
            }

            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
    */
}
