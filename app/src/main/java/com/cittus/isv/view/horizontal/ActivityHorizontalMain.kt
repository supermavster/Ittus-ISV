package com.cittus.isv.view.horizontal

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import android.widget.RadioButton
import com.cittus.isv.DAO.DAOConnection
import com.cittus.isv.R
import com.cittus.isv.controller.MainImage
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.EndPoints
import com.cittus.isv.view.MainActivity
import com.cittus.isv.view.tabs.TabMain
import kotlinx.android.synthetic.main.activity_horizontal_main.*
import kotlinx.android.synthetic.main.tab_information.view.*

class ActivityHorizontalMain : AppCompatActivity() {


    // Variables
    var carril = "";
    var porcentaje = "";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_main)

        initProcess()
    }


    private fun initProcess(){

        // Ubicacion Elementos
        toggleButtonAction()

        // Carril
        carril()

        // Save Button
        save()
    }

    var main = false
    private fun toggleButtonAction(){
        btn_intersection.setOnClickListener {
            // Block Other Button
            btn_intersection.setLinkTextColor(android.R.attr.colorPrimary)
            btn_stretch.setLinkTextColor(Color.RED)
            btn_stretch.isClickable = main
            main = !main
            // Make Images
            if(btn_intersection.isClickable)
            makeActivityImages(R.string.title_horizontal_intersection,EndPoints.URL_GET_HORIZONTAL_INTERSECTION,false)
        }
        btn_stretch.setOnClickListener {
            // Block Other Button
            btn_stretch.setLinkTextColor(android.R.attr.colorPrimary)
            btn_intersection.setLinkTextColor(Color.RED)
            btn_intersection.isClickable = main
            main = !main
            // Make Images
            if(btn_stretch.isClickable)
            makeActivityImages(R.string.title_horizontal_stretch,EndPoints.URL_GET_HORIZONTAL_STRETCH)
        }
    }


    private fun carril(){
        //Populate NumberPicker values from String array values
        var values = arrayOf(
            "Carril 1",
            "Carril 2",
            "Carril 3",
            "Carril 4",
            "Carril 5",
            "Carril 6"
        )

        var textPicker:NumberPicker = findViewById(R.id.txt_location_signal)
        textPicker.setMinValue(0)
        textPicker.setMaxValue(values.size - 1)
        textPicker.setDisplayedValues(values)
        textPicker.setWrapSelectorWheel(true)

        textPicker.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(numberPicker: NumberPicker, i: Int, i1: Int) {
                carril = values[i1]
                Log.i("Selected Text : " ,values[i1])

            }
        })

        //Populate NumberPicker values from String array values
        var valTemp: ArrayList<String> = ArrayList<String>()
        for (i in 100 downTo 0 step 10) {
            valTemp.add("${i} %")
        }

        textPicker = findViewById(R.id.txt_percentage_coverage)
        textPicker.setMinValue(0)
        textPicker.setMaxValue(valTemp.size - 1)
        textPicker.setDisplayedValues(valTemp.toTypedArray())
        textPicker.setWrapSelectorWheel(true)

        textPicker.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(numberPicker: NumberPicker, i: Int, i1: Int) {
                porcentaje = valTemp.get(i1)
                Log.i("Selected Text : " ,valTemp.get(i1))
            }
        })
    }

    private fun save(){
        btn_save_ths.setOnClickListener(View.OnClickListener {
            // TODO Add extras or a data URI to this intent as appropriate.
            var intentTemp: Intent = Intent()
            var data = getData();
                intentTemp.putExtra("getData", data)
                setResult(ActionsRequest.GET_HORIZONTAL_VALUES, intentTemp)
            finish()

        })
    }

    fun getData():ArrayList<String>{
        var tempValues = ArrayList<String>();

        // Direccion
        var rb: RadioButton = findViewById(rg_directional.checkedRadioButtonId)
        tempValues.add("Direccion:" + rb.text)

        // Location in the Trayect
        var btnLocationMain: String = ""
        if (btn_stretch.isChecked) {
            btnLocationMain = btn_stretch.textOn.toString()
        } else if (btn_intersection.isChecked) {
            btnLocationMain = btn_intersection.textOn.toString()
        }
        tempValues.add("LocationTrayect:" + btnLocationMain)

        // Carriles
        tempValues.add("Carril:"+carril)

        tempValues.add("Porcentaje:"+porcentaje)

        return tempValues;
    }

    private fun makeActivityImages(title: Int, url_img:String,code:Boolean=true,description: Boolean=true){
        var intent = Intent(this, MainImage::class.java)
        intent.putExtra("title", resources.getString(title))
        intent.putExtra("url_img",url_img)
        intent.putExtra("code",code)
        intent.putExtra("description",description)
        startActivity(intent)
    }
}
