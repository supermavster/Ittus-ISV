package com.cittus.isv.view.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.DAO.DAOConnection
import com.cittus.isv.R
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.EndPoints
import com.cittus.isv.view.MainActivity

class MunicipalitiesActivity : Fragment() {

    var connection : DAOConnection? = null
    var maxIDInventario = ""
    var maxIDListSignal = ""
    private var viewMain: View? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_municipalities, container, false)
        viewMain = view
        connection = DAOConnection(view.context)
        // Init process
        init()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val venName = arguments?.getBoolean("isLogin")
        Log.e("isLogin",venName.toString())

        /*val someDataClass: SomeDataClass? = arguments?.getParcelable("custom_object")
        someDataClass?.let {
            customObjectField.text = it.someField
            customObjectNumber.text = it.anotherField.toString()
        }
        */
    }





    private fun init() {
        viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_municipio).isEnabled = false

        viewMain!!.findViewById<Button>(R.id.buttonMain).isEnabled = false

        // MAx ID Inventario - maxIDListSignal
        maxIDInventario = connection!!.loadElement(EndPoints.URL_GET_MAX_ID+"inventario")
        maxIDListSignal = connection!!.loadElement(EndPoints.URL_GET_MAX_ID+"lista")

        // Departamento
        var array = connection!!.loadElements(EndPoints.URL_GET_DEPARTAMENTOS)
        makeAutocomplete(array,viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_departamento))

        // Set Actions
        viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_departamento).onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            // Active Buttons
            viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_municipio).isEnabled = true
            viewMain!!.findViewById<Button>(R.id.buttonMain).isEnabled = true
            // Add Actions
            val selectedItem = parent.getItemAtPosition(position).toString()
            array = connection!!.loadElements(EndPoints.URL_GET_SEARCH_MUNICIPIOS+selectedItem)
            makeAutocomplete(array,viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_municipio))
        }


        viewMain!!.findViewById<Button>(R.id.buttonMain).setOnClickListener { view ->

            var intentTemp = Intent(viewMain!!.context, MainActivity::class.java)
            var data =ArrayList<String>()
            // TODO: Location DATA MAIN (1)
            // 0 -> Id Inventario
            // 1 -> Id Lista Senal
            // 2 -> Municipio
            // 3 -> Departamento
            // 4 -> Id Max Signal
            // Get Max Inventario
            if(maxIDInventario.isEmpty()){
                maxIDInventario = connection!!.loadElement(EndPoints.URL_GET_MAX_ID+"inventario")
            }
            data.add(0,maxIDInventario) // 0 -> Id Inventario
            // Get Max Signal
            if(maxIDListSignal.isEmpty()){
                maxIDListSignal = connection!!.loadElement(EndPoints.URL_GET_MAX_ID+"lista")
            }
            data.add(1,maxIDListSignal) // 1 -> Id Lista Senal

            // Get Municipio
            data.add(2,viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_municipio).text.toString()) // 2 -> Municipio

            // Get Municipio
            data.add(3,viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_departamento).text.toString()) // 3 -> Departamento

            // Get Max Id Singal
            data.add(4,maxIDListSignal);//connection.loadElement(EndPoints.URL_GET_MAX_ID+"senal"))

            intentTemp.putExtra("getData", data)
            //startActivityForResult(intentTemp, ActionsRequest.GET_INIT)

            Navigation.findNavController(view).navigate(R.id.geolocalizationActivity)

        }
    }

    private fun makeAutocomplete(array: ArrayList<String>, auto_complete_text_view: AutoCompleteTextView){
        // Initialize a new array adapter object
        val adapter = ArrayAdapter<String>(
            context, // Context
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
