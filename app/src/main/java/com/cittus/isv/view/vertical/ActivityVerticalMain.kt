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
            startActivity(intent)
        }
        btn_vertical_informative.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun btnRegulatory(){
        var intent = makeActivityImages(R.string.title_vertical_regulatory,EndPoints.URL_GET_VERTICAL_REGULATORY)
        ibtn_vertical_regulatory.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_IMAGES)
        }
        btn_vertical_regulatory.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_IMAGES)
        }
    }

    private fun btnPreventive(){
        var intent = makeActivityImages(R.string.title_vertical_preventives,EndPoints.URL_GET_VERTICAL_PREVENTIVES)
        ibtn_vertical_preventive.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_IMAGES)
        }
        btn_vertical_preventive.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_IMAGES)
        }
    }

    private fun btnWork(){
        var intent = makeActivityImages(R.string.title_vertical_work,EndPoints.URL_GET_VERTICAL_WORK)
        ibtn_vertical_work.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_IMAGES)
        }
        btn_vertical_work.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_IMAGES)
        }
    }

    private fun btnCycleRoute(){

        var intent = makeActivityImages(R.string.title_vertical_cycle_route,EndPoints.URL_GET_VERTICAL_CYCLE_ROUTE)
        ibtn_vertical_cycle_route.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_IMAGES)
        }
        btn_vertical_cycle_route.setOnClickListener {
            startActivityForResult(intent, ActionsRequest.GET_IMAGES)
        }
    }

    // Add class
    private fun makeActivityImages(title: Int, url_img:String,code:Boolean=true,description: Boolean=false): Intent {
        var intent: Intent = Intent(this, MainImage::class.java)
        intent.putExtra("title", resources.getString(title))
        intent.putExtra("url_img",url_img)
        intent.putExtra("code",code)
        intent.putExtra("description",description)
        return(intent)
    }

    // Camera Action // Return Intend Action
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i("IMAGES",requestCode.toString()+"->"+resultCode+"->"+data.toString())
        when (requestCode) {
            ActionsRequest.GET_IMAGES -> {
                if (data != null){
                    val extras = data!!.extras ?: return
                    val arrayListMain = extras.getStringArrayList("getData")

                    // Get TO the MAIN ACTIVITY
                    var intentTemp: Intent = Intent()
                    intentTemp.putExtra("getData", arrayListMain)
                    setResult(ActionsRequest.GET_IMAGES, intentTemp)
                    Log.i("getData", "getData:" + arrayListMain.toString())
                    // End Activity
                    finish()
                }
            }
            else->{
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
