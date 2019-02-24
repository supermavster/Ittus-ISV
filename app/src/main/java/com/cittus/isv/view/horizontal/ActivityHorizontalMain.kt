package com.cittus.isv.view.horizontal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.Toast
import com.cittus.isv.R
import com.cittus.isv.controller.MainImage
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.EndPoints
import kotlinx.android.synthetic.main.activity_horizontal_main.*

class ActivityHorizontalMain : AppCompatActivity() {


    // Variables
    var carril = "";
    var porcentaje = "";
    // Exception
    var exceptionMain: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_main)
        if (savedInstanceState != null) {
            val value = savedInstanceState.getStringArrayList("getDataMain")
            var rb: RadioButton = findViewById(rg_directional.checkedRadioButtonId)
            rb.text = value[0] // 1-0 -> Direccion

            // Location in the Trayect
            if (value[1].equals(btn_stretch.textOn.toString(), true)) {
                btn_stretch.isPressed = true
                btn_intersection.isPressed = false
            } else if (value[1].equals(btn_intersection.textOn.toString(), true)) {
                btn_intersection.isPressed = true
                btn_stretch.isPressed = false
            }
        } else {
            initProcess()
        }

    }
    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putStringArrayList("getDataMain", getData())
        super.onSaveInstanceState(savedInstanceState)
    }


    private fun initProcess() {

        // Ubicacion Elementos
        toggleButtonAction()

        // Carril
        carril()

        // Save Button
        save()
    }

    var main = false
    private fun toggleButtonAction() {
        btn_intersection.setOnClickListener {
            // Block Other Button
            btn_intersection.setLinkTextColor(android.R.attr.colorPrimary)
            btn_stretch.setLinkTextColor(Color.RED)
            btn_stretch.isClickable = main

            // Make Images
            if (!btn_stretch.isClickable)
                makeActivityImages(R.string.title_horizontal_intersection, EndPoints.URL_GET_HORIZONTAL_INTERSECTION)
            main = !main
        }
        btn_stretch.setOnClickListener {
            // Block Other Button
            btn_stretch.setLinkTextColor(android.R.attr.colorPrimary)
            btn_intersection.setLinkTextColor(Color.RED)
            btn_intersection.isClickable = main

            // Make Images
            if (!btn_intersection.isClickable)
                makeActivityImages(R.string.title_horizontal_stretch, EndPoints.URL_GET_HORIZONTAL_STRETCH)
            main = !main
        }
    }


    private fun carril() {
        //Populate NumberPicker values from String array values
        var values = arrayOf(
            "Carril 1",
            "Carril 2",
            "Carril 3",
            "Carril 4",
            "Carril 5",
            "Carril 6"
        )

        var textPicker: NumberPicker = findViewById(R.id.txt_location_signal)
        textPicker.setMinValue(0)
        textPicker.setMaxValue(values.size - 1)
        textPicker.setDisplayedValues(values)
        textPicker.setWrapSelectorWheel(true)

        textPicker.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(numberPicker: NumberPicker, i: Int, i1: Int) {
                carril = values[i1]
                Log.i("Selected Text : ", values[i1])

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
                Log.i("Selected Text : ", valTemp.get(i1))
            }
        })
    }

    private fun save() {
        btn_save_ths.setOnClickListener(View.OnClickListener {
            // Get Data of this Activity
            var data = getData();
            // Check Errors
            if (exceptionMain === false) {
                // Init Process TO send  MAIN ACTIVIVTY
                var intentTemp: Intent = Intent()
                intentTemp.putExtra("getData", data)
                setResult(ActionsRequest.GET_HORIZONTAL_VALUES, intentTemp)
                finish()
            }
        })
    }

    fun getData(): ArrayList<String> {
        var tempValues = ArrayList<String>();
        // TODO: ISV Horizontal DATA MAIN
        // 1-0 -> Direccion
        // 1-1 -> Location Trayecto
        // 1-2 -> Carril
        // 1-3 -> Porcentaje
        try {
            // Direccion
            var rb: RadioButton = findViewById(rg_directional.checkedRadioButtonId)
            tempValues.add(0, rb.text.toString()) // 1-0 -> Direccion

            // Location in the Trayect
            var btnLocationMain: String = ""
            if (btn_stretch.isChecked) {
                btnLocationMain = btn_stretch.textOn.toString()
            } else if (btn_intersection.isChecked) {
                btnLocationMain = btn_intersection.textOn.toString()
            }
            if (btnLocationMain.isNotEmpty()) {
                tempValues.add(1, btnLocationMain)// 1-1 -> Location Trayecto
            } else {
                throw Exception("Location was not Select")
            }

            // Carriles
            tempValues.add(2, carril) // 1-2 -> Carril

            tempValues.add(3, porcentaje) // 1-3 -> Porcentaje

            exceptionMain = false
        } catch (e: Exception) {
            Log.e("NULL", e.message)
            var errorMain = when {
                e.message.equals(
                    "findViewById(rg_directional.checkedRadioButtonId) must not be null",
                    true
                ) -> "Seleccione el Sentido de trafico"
                e.message.equals(
                    "Location was not Select",
                    true
                ) -> "Seleccione la Ubicación de la señal en el trayecto"
                else -> "ERROR: " + e.message
            }
            Toast.makeText(this, "$errorMain  para poder continuar", Toast.LENGTH_SHORT).show()
            exceptionMain = true
        }

        return tempValues;
    }

    private fun makeActivityImages(title: Int, url_img: String, code: Boolean = true, description: Boolean = true) {
        var intent = Intent(this, MainImage::class.java)
        intent.putExtra("title", resources.getString(title))
        intent.putExtra("url_img", url_img)
        intent.putExtra("code", code)
        intent.putExtra("description", description)
        startActivity(intent)
    }
}
