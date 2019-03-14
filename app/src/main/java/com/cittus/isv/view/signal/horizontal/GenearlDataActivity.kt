package com.cittus.isv.view.signal.horizontal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.model.*

class GenearlDataActivity : Fragment() {

    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    private var signalArrayList = ArrayList<CittusISV>()

    // Variables Class
    // Variables
    var carril = "";
    var porcentaje = "";
    // Exception
    var exceptionMain: Boolean = false
    var elementsBaseImage = java.util.ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_genearl_data, container, false)
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
        // Carril
        carril()

        // Save Button
        save()
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

        var textPicker: NumberPicker = viewMain!!.findViewById<NumberPicker>(R.id.txt_location_signal)
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
            valTemp.add("${i}%")
        }

        textPicker = viewMain!!.findViewById<NumberPicker>(R.id.txt_percentage_coverage)
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


    fun getDataMain(): ArrayList<String> {
        var tempValues = ArrayList<String>();
        // TODO: ISV Horizontal DATA MAIN
        // 1-0 -> Direccion
        // 1-2 -> Carril
        // 1-3 -> Porcentaje
        try {
            // Direccion
            var rg_directional = viewMain!!.findViewById<RadioGroup>(R.id.rg_directional)
            var rb: RadioButton = viewMain!!.findViewById<RadioButton>(rg_directional.checkedRadioButtonId)
            tempValues.add(0, rb.text.toString()) // 1-0 -> Direccion

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
            //Toast.makeText(this, "$errorMain  para poder continuar", Toast.LENGTH_SHORT).show()
            exceptionMain = true
        }

        return tempValues;
    }

    private fun save() {
        viewMain!!.findViewById<NumberPicker>(R.id.btn_next_address).setOnClickListener {

            var dataTemp = getDataMain()
            // 1-0 -> Direccion
            // 1-2 -> Carril
            // 1-3 -> Porcentaje

            var horizontalSignal = HorizontalSignal()
            horizontalSignal.locationOnTheWay = ""
            horizontalSignal.directionJourney = dataTemp.get(0)
            horizontalSignal.rail = dataTemp.get(2)
            horizontalSignal.percentage = dataTemp.get(3)

            // Add data to Object
            signalArrayList.get(0).horizontalSignal = horizontalSignal

            // Make Object Main
            var cittusDB: CittusListSignal =
                CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
            // Set and Send Data Main
            bundle.putParcelable("CittusDB", cittusDB)
            // Start Activity
            Navigation.findNavController(viewMain!!).navigate(R.id.photoGPSActivity, bundle)
        }
    }

}
