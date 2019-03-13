package com.cittus.isv.view.base

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
import com.cittus.isv.model.CittusListSignal
import com.cittus.isv.model.EndPoints
import com.cittus.isv.model.Municipalities
import java.util.*

class MunicipalitiesActivity : Fragment() {

    // Main Variables
    private lateinit var viewMain: View
    private lateinit var connection: DAOConnection

    // Make Bundle
    val bundle = Bundle()
    var login = 0



    var maxIDInventario = ""
    var maxIDListSignal = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Init View
        viewMain = inflater.inflate(R.layout.activity_municipalities, container, false)
        // Init Connection
        connection = DAOConnection(viewMain.context)
        return viewMain
    }

    // TODO: Get Data - Login
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*/ Get Values from Login
        val isLogin = arguments?.getBoolean("isLogin")
        // Add your data from getFactualResults method to bundle
        Log.e("isLogin", isLogin.toString())*/

        val someDataClass: CittusListSignal? = arguments?.getParcelable("CittusDB")
        someDataClass?.let {
            login = it.login
        }
        if (login === 1) {
            // Init Process
            initProcess()
        }
    }

    private fun initProcess() {

        // Lock Button and TextView
        viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_municipio).isEnabled = false
        viewMain!!.findViewById<Button>(R.id.buttonMain).isEnabled = false

        // MAx ID Inventario - maxIDListSignal
        maxIDInventario = connection!!.getDataSingle(EndPoints.URL_GET_MAX_ID + "inventario")
        maxIDListSignal = connection!!.getDataSingle(EndPoints.URL_GET_MAX_ID + "lista")

        // Departamento
        var array = connection!!.getData(EndPoints.URL_GET_DEPARTAMENTOS)
        makeAutocomplete(array, viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_departamento))

        // Set Actions
        viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_departamento).onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                // Active Buttons
                viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_municipio).isEnabled = true
                viewMain!!.findViewById<Button>(R.id.buttonMain).isEnabled = true
                // Add Actions
                val selectedItem = parent.getItemAtPosition(position).toString()
                array = connection!!.getData(EndPoints.URL_GET_SEARCH_MUNICIPIOS + selectedItem)
                makeAutocomplete(array, viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_municipio))
            }


        viewMain!!.findViewById<Button>(R.id.buttonMain).setOnClickListener { view ->
            var data = ArrayList<String>()
            // TODO: Location DATA MAIN (1)
            // 0 -> Id Inventario
            // 1 -> Id Lista Senal
            // 2 -> Municipio
            // 3 -> Departamento
            // 4 -> Id Max Signal

            // Get Max Inventario
            if (maxIDInventario.isEmpty()) {
                maxIDInventario = connection!!.getDataSingle(EndPoints.URL_GET_MAX_ID + "inventario")
            }
            data.add(0, maxIDInventario) // 0 -> Id Inventario
            // Get Max Signal
            if (maxIDListSignal.isEmpty()) {
                maxIDListSignal = connection!!.getDataSingle(EndPoints.URL_GET_MAX_ID + "lista")
            }
            data.add(1, maxIDListSignal) // 1 -> Id Lista Senal

            // Get Municipio
            data.add(
                2,
                viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_municipio).text.toString()
            ) // 2 -> Municipio

            // Get Municipio
            data.add(
                3,
                viewMain!!.findViewById<AutoCompleteTextView>(R.id.auto_complete_departamento).text.toString()
            ) // 3 -> Departamento

            // Get Max Id Singal
            data.add(4, maxIDListSignal);//connection.loadElement(EndPoints.URL_GET_MAX_ID+"senal"))

            // Set Data
            // 0 -> Id Inventario
            // 1 -> Id Lista Senal
            // 2 -> Municipio
            // 3 -> Departamento
            // 4 -> Id Max Signal
            var idInventario = data.get(0).toInt()
            var idListSignal = data.get(1).toInt()
            var idMaxSignal = data.get(4).toInt()
            var nameMunicipal = data.get(2)
            var nameDepartment = data.get(3)
            Log.e("Data", data.toString())

            // Make Object Municipalities
            val municipalities = Municipalities(idInventario, idListSignal, idMaxSignal, nameMunicipal, nameDepartment)
            // Make Object Main
            var cittusDB: CittusListSignal = CittusListSignal(login, municipalities, null, null)
            Log.e("Id", cittusDB.toString())
            // Set and Send Data Main
            bundle.putParcelable("CittusDB", cittusDB)
            // Init Action
            Navigation.findNavController(viewMain!!).navigate(R.id.geolocalizationActivity, bundle)

        }
    }

    private fun makeAutocomplete(array: ArrayList<String>, auto_complete_text_view: AutoCompleteTextView) {
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
        auto_complete_text_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            //Toast.makeText(applicationContext,"Selected : $selectedItem", Toast.LENGTH_SHORT).show()
        }


        // Set a dismiss listener for auto complete text view
        auto_complete_text_view.setOnDismissListener {
            //Toast.makeText(applicationContext,"Suggestion closed.", Toast.LENGTH_SHORT).show()
        }


        // Set a focus change listener for auto complete text view
        auto_complete_text_view.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                // Display the suggestion dropdown on focus
                auto_complete_text_view.showDropDown()
            }
        }
    }
}