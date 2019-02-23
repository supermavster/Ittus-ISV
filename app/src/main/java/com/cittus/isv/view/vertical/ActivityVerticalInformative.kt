package com.cittus.isv.view.vertical

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cittus.isv.R
import com.cittus.isv.controller.MainImage
import com.cittus.isv.model.EndPoints
import kotlinx.android.synthetic.main.activity_vertical_informative.*
import java.util.*

class ActivityVerticalInformative : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_informative)
        // Init Process
        initProcess()
    }

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
            startActivity(intent)
        }
        btn_vts_informative_services.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun btnTouristic(){

        var intent = makeActivityImages(R.string.title_vertical_info_turistic,EndPoints.URL_GET_VERTICAL_INFO_TOURIST)
        ibtn_vts_informative_turist.setOnClickListener {
            startActivity(intent)
        }
        btn_vts_informative_turist.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun btnLocation(){

        var intent = makeActivityImages(R.string.title_vertical_info_localization,EndPoints.URL_GET_VERTICAL_INFO_LOCATION)
        ibtn_vts_informative_location.setOnClickListener {
            startActivity(intent)
        }
        btn_vts_informative_location.setOnClickListener {
            startActivity(intent)
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
}
