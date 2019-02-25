package com.cittus.isv.view.vertical

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cittus.isv.R
import com.cittus.isv.controller.MainImage
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.EndPoints
import com.cittus.isv.view.horizontal.ActivityHorizontalMain
import kotlinx.android.synthetic.main.activity_vertical_main.*

class ActivityVerticalMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_main)
        // Init Process
        initProcess()
    }

    private fun initProcess(){
        // BTN Information
        btnInformation()
        // BTN Regulatory
        btnRegulatory()
        // BTN Preventive
        btnPreventive()
        // BTN Work
        btnWork()
        // BTN Cycle Route
        btnCycleRoute()
    }

    private fun btnInformation(){
        var intent = Intent(this, ActivityVerticalInformative::class.java);
        ibtn_vertical_informative.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES and ActionsRequest.GET_VERTICAL_VALUES)
        }
        btn_vertical_informative.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES and ActionsRequest.GET_VERTICAL_VALUES)
        }
    }

    private fun btnRegulatory(){
        var intent = makeActivityImages(R.string.title_vertical_regulatory,EndPoints.URL_GET_VERTICAL_REGULATORY)
        ibtn_vertical_regulatory.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
        btn_vertical_regulatory.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
    }

    private fun btnPreventive(){
        var intent = makeActivityImages(R.string.title_vertical_preventives,EndPoints.URL_GET_VERTICAL_PREVENTIVES)
        ibtn_vertical_preventive.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
        btn_vertical_preventive.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
    }

    private fun btnWork(){
        var intent = makeActivityImages(R.string.title_vertical_work,EndPoints.URL_GET_VERTICAL_WORK)
        ibtn_vertical_work.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
        btn_vertical_work.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
    }

    private fun btnCycleRoute(){

        var intent = makeActivityImages(R.string.title_vertical_cycle_route,EndPoints.URL_GET_VERTICAL_CYCLE_ROUTE)
        ibtn_vertical_cycle_route.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
        btn_vertical_cycle_route.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        }
    }

    // Add class
    private fun makeActivityImages(title: Int, url_img:String,code:Boolean=true,description: Boolean=false): Intent {
        var intent: Intent = Intent(this, MainImage::class.java)
        intent.putExtra("title", resources.getString(title))
        intent.putExtra("url_img",url_img)
        intent.putExtra("code",code)
        intent.putExtra("description",description)
        intent.putExtra("Action",ActionsRequest.GET_VERTICAL_IMAGES_VALUES)
        return(intent)
    }

    var exceptionMain: Boolean = false
    private fun getData(): ArrayList<String> {

        return ArrayList<String>()
    }

    // Actions To Return
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i("Vertical Activity", requestCode.toString() + "->" + resultCode + "->" + data.toString())
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
                        setResult(ActionsRequest.GET_VERTICAL_VALUES, intentTemp)
                        finish()
                    }
                }
            }
            ActionsRequest.GET_VERTICAL_IMAGES_VALUES and ActionsRequest.GET_VERTICAL_VALUES->{
                if (data != null) {
                    val extras = data!!.extras ?: return
                    val titleTemp = extras.getString("getTitle")
                    val elementsBaseImage = extras.getStringArrayList("getDataImages")
                    Log.e("getData", "getDataImages:$elementsBaseImage")
                    // Check Errors
                    if (exceptionMain === false) {
                        // Init Process TO send  MAIN ACTIVIVTY
                        var intentTemp: Intent = Intent()
                        intentTemp.putExtra("getTitle", titleTemp)
                        intentTemp.putExtra("getDataImages", elementsBaseImage)
                        setResult(ActionsRequest.GET_VERTICAL_VALUES, intentTemp)
                        finish()
                    }
                }
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
