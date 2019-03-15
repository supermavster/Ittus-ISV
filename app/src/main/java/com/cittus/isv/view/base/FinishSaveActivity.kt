package com.cittus.isv.view.base

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.DAO.DAOConnection
import com.cittus.isv.R
import com.cittus.isv.complements.uploadFiles.MakeToUpoad
import com.cittus.isv.model.CittusISV
import com.cittus.isv.model.CittusListSignal
import com.cittus.isv.model.GeolocationCardinalImages
import com.cittus.isv.model.Municipalities
import java.io.File

class FinishSaveActivity : Fragment() {

    // Main Variables
    private lateinit var viewMain: View

    // Make Bundle
    val bundle = Bundle()
    var login = 0
    private var municipalities: Municipalities? = null
    private var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null
    private var signalArrayList = ArrayList<CittusISV>()

    // Variables Class
    var maxID: String = ""
    // Exception
    var exceptionMain: Boolean = false
    // Make Clases
    var message = "";


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewMain = inflater.inflate(R.layout.activity_finish_save, container, false)
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

        // Btn Save Signal
        btnSave()
    }

    private fun btnSave() {
        viewMain.findViewById<Button>(R.id.btn_save_all).setOnClickListener {
            // Make Object Main
            var cittusDB: CittusListSignal =
                CittusListSignal(login, municipalities, signalArrayList, geolocationCardinalImages)
            // Set and Send Data Main
            bundle.putParcelable("CittusDB", cittusDB)
            // Save Data
            saveAllElements()
            // Start Activity
            Navigation.findNavController(viewMain!!).navigate(R.id.typeSignalActivity, bundle)
        }
    }

    private fun saveAllElements() {
        if (municipalities != null && signalArrayList != null) {
            // Upload to Data Base
            uploadDataBase()
        } else {
            message = "Error faltal, no se pudo crear el inventario"
        }
        Snackbar.make(viewMain, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }


    private fun uploadDataBase() {
        if (signalArrayList != null && exceptionMain === false) {
            // Set Dates

            val bd = DAOConnection(viewMain.context)
            if (bd.addSignal(signalArrayList.get(0)!!) === true) {
                message = "Datos añadidos con exito."
                // Upload Images
                uploadImages()
                // Reset Views contBaseID
            } else {
                message = "No se han podido subir los datos, reviselos por favor."
            }
        } else {
            message = "No se puede crear la señal, revise los datos por favor."
        }
    }

    private fun uploadImages() {
        var makeToUpoad: MakeToUpoad = MakeToUpoad(viewMain.context);
        var uri = Uri.fromFile(File(signalArrayList.get(0)!!.imagesByCode!!.pathImagen))
        makeToUpoad.showFileChooser(uri)


    }
}
