package com.cittus.isv.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.cittus.isv.DAO.DAOConnection
import com.cittus.isv.R
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.EndPoints
import kotlinx.android.synthetic.main.activity_init.*

class InitActivity : AppCompatActivity() {

    var connection : DAOConnection = DAOConnection(this)
    var maxIDInventario = ""
    var maxIDListSignal = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        // Init process
        init()
    }

    private fun init() {
        auto_complete_municipio.isEnabled = false
        buttonMain.isEnabled = false

        // MAx ID Inventario - maxIDListSignal
        maxIDInventario = connection.loadElement(EndPoints.URL_GET_MAX_ID+"inventario")
        maxIDListSignal = connection.loadElement(EndPoints.URL_GET_MAX_ID+"lista")

        // Departamento
        var array = connection.loadElements(EndPoints.URL_GET_DEPARTAMENTOS)
        makeAutocomplete(array,auto_complete_departamento)

        // Set Actions
        auto_complete_departamento.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            // Active Buttons
            auto_complete_municipio.isEnabled = true
            buttonMain.isEnabled = true
            // Add Actions
            val selectedItem = parent.getItemAtPosition(position).toString()
            array = connection.loadElements(EndPoints.URL_GET_SEARCH_MUNICIPIOS+selectedItem)
            makeAutocomplete(array,auto_complete_municipio)
        }

        buttonMain.setOnClickListener {
            var intentTemp = Intent(this,MainActivity::class.java)
            var data =ArrayList<String>()
                // TODO: Location DATA MAIN (1)
                // 0 -> Id Inventario
                // 1 -> Id Lista Senal
                // 2 -> Municipio
                // 3 -> Departamento
                // 4 -> Id Max Signal
                // Get Max Inventario
                if(maxIDInventario.isEmpty()){
                    maxIDInventario = connection.loadElement(EndPoints.URL_GET_MAX_ID+"inventario")
                }
                data.add(0,maxIDInventario) // 0 -> Id Inventario
                // Get Max Signal
                if(maxIDListSignal.isEmpty()){
                    maxIDListSignal = connection.loadElement(EndPoints.URL_GET_MAX_ID+"lista")
                }
                data.add(1,maxIDListSignal) // 1 -> Id Lista Senal

                // Get Municipio
                data.add(2,auto_complete_municipio.text.toString()) // 2 -> Municipio

                // Get Municipio
                data.add(3,auto_complete_departamento.text.toString()) // 3 -> Departamento

                // Get Max Id Singal
                data.add(4,maxIDListSignal);//connection.loadElement(EndPoints.URL_GET_MAX_ID+"senal"))

                intentTemp.putExtra("getData", data)
                startActivityForResult(intentTemp,ActionsRequest.GET_INIT)

        }
    }

    private fun makeAutocomplete(array: ArrayList<String>, auto_complete_text_view: AutoCompleteTextView){
        // Initialize a new array adapter object
        val adapter = ArrayAdapter<String>(
            this, // Context
            android.R.layout.simple_dropdown_item_1line, // Layout
            array // Array
        )

        // Set the AutoCompleteTextView adapter
        auto_complete_text_view.setAdapter(adapter)


        // Auto complete threshold
        // The minimum number of characters to type to show the drop down
        auto_complete_text_view.threshold = 1


        // Set an item click listener for auto complete text view
        auto_complete_text_view.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            //Toast.makeText(applicationContext,"Selected : $selectedItem", Toast.LENGTH_SHORT).show()
        }


        // Set a dismiss listener for auto complete text view
        auto_complete_text_view.setOnDismissListener {
            //Toast.makeText(applicationContext,"Suggestion closed.", Toast.LENGTH_SHORT).show()
        }


        // Set a click listener for root layout
        root_layout.setOnClickListener{
            val text = auto_complete_text_view.text
            //Toast.makeText(applicationContext,"Inputted : $text", Toast.LENGTH_SHORT).show()
        }


        // Set a focus change listener for auto complete text view
        auto_complete_text_view.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                // Display the suggestion dropdown on focus
                auto_complete_text_view.showDropDown()
            }
        }
    }

}
