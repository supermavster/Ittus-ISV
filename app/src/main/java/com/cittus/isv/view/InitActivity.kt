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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        // Init process
        init()
    }

    private fun init() {
        auto_complete_municipio.isEnabled = false
        buttonMain.isEnabled = false
        // Departamento
        var array = connection.loadElements(EndPoints.URL_GET_DEPARTAMENTOS)
        makeAutocomplete(array,auto_complete_departamento)

        // Municipio array = connection.loadElements(EndPoints.URL_GET_MUNICIPIOS) makeAutocomplete(array,auto_complete_municipio)

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
            var intentTemp: Intent = Intent(this,MainActivity::class.java)
            var data =ArrayList<String>()

                // Get Municipio
                data.add(auto_complete_municipio.text.toString())

                // Get Municipio
                data.add(auto_complete_departamento.text.toString())
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
            Toast.makeText(applicationContext,"Selected : $selectedItem", Toast.LENGTH_SHORT).show()
        }


        // Set a dismiss listener for auto complete text view
        auto_complete_text_view.setOnDismissListener {
            Toast.makeText(applicationContext,"Suggestion closed.", Toast.LENGTH_SHORT).show()
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
