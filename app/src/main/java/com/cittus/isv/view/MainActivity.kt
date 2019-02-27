package com.cittus.isv.view


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cittus.isv.DAO.DAOConnection
import com.cittus.isv.R
import com.cittus.isv.complements.uploadFiles.MakeToUpoad
import com.cittus.isv.model.*
import com.cittus.isv.view.tabs.TabGeneralData
import com.cittus.isv.view.tabs.TabInformation
import com.cittus.isv.view.tabs.TabMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    var mainActivity: Activity = this
    var connection : DAOConnection = DAOConnection(this)
    var maxID:String = ""


    // Make Clases
    private var inventario: CittusInventario? = null
    private var listSignal: CittusListSignal? = null
    private var signalMain: CittusSignal? = null
    var message = "";
    private var signalMainArray: ArrayList<CittusSignal> = ArrayList<CittusSignal>()
    var contBaseID = 0;

    // Variables Table
    var tabMain: TabMain = TabMain(mainActivity)
    var tabInformation: TabInformation = TabInformation(mainActivity)
    var tabGeneralData: TabGeneralData = TabGeneralData(mainActivity)

    // Exception
    var exceptionMain: Boolean = false

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        resetData()
        //TODO: Get Main DATA - INVENTARIO
        getAndSetDataMain()

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        // Hide Elements container.getChildAt(0)).getChildAt(0).setVisibility(View.GONE);

        //mSectionsPagerAdapter.getItem(0).view.visibility = View.GONE
        //tab_data_general!!.visibility = View.GONE

  //      tabs.removeTab(tabs.getTabAt(2));
//        tabs.removeTab(tabs.getTabAt(3));
        maxID = connection.loadElement(EndPoints.URL_GET_MAX_ID+"lista")

        fab.setOnClickListener { view ->
            // Init Data
            maxID = connection.loadElement(EndPoints.URL_GET_MAX_ID+"lista")
            Log.e("ID",maxID)
            // Save
            saveAllElements(view)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            return when (position) {
                0 -> tabMain
                1 -> tabInformation
                2 -> tabGeneralData
                else -> Fragment()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

var horizontalItems = ArrayList<String>()
    var verticalItems:String=""
    var dataImagenes = ArrayList<String>()

    // Camera Action // Return Intend Action
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i("Main Activity", requestCode.toString() + "->" + resultCode + "->" + data.toString())
        when (requestCode) {
            ActionsRequest.TAKE_PHOTO_REQUEST_FRONT and RESULT_OK -> tabMain.processCapturedPhoto(tabMain.getTakePictureMainFront())
            ActionsRequest.TAKE_PHOTO_REQUEST_BACK and RESULT_OK -> tabMain.processCapturedPhoto(tabMain.getTakePictureMainBack())
            ActionsRequest.TAKE_PHOTO_REQUEST_PLAQUE and RESULT_OK -> tabMain.processCapturedPhoto(tabMain.getTakePictureMainPlaque())
            ActionsRequest.GET_HORIZONTAL_VALUES -> {
                if (data != null) {
                    val extras = data!!.extras ?: return
                    horizontalItems = extras.getStringArrayList("getData")
                    dataImagenes = extras.getStringArrayList("getDataImages")
                    Log.e("getData Horizontal", "getData:$horizontalItems  Images:$dataImagenes")
                }
            }
            ActionsRequest.GET_VERTICAL_VALUES or ActionsRequest.GET_VERTICAL_IMAGES_VALUES->{
                if (data != null) {
                    val extras = data!!.extras ?: return
                    verticalItems = extras.getString("getTitle")
                    dataImagenes = extras.getStringArrayList("getDataImages")
                    Log.e("getData Vertical", "getData:$verticalItems  Images:$dataImagenes")
                }
            }
            ActionsRequest.GET_ID->{
                if (data == null) {
                    //no data present
                    return
                }
                val extras = data!!.extras ?: return
                contBaseID = extras.getInt("contBaseID")
                Log.e("Show ID",contBaseID.toString())
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun getAndSetDataMain() {

        val data = intent.getStringArrayListExtra("getData")
        // Init Inventario
        if (data != null && inventario == null) {
            // TODO: Location DATA MAIN (2)
            // 0 -> Id Inventario
            // 1 -> Id Lista Senal
            // 2 -> Municipio
            // 3 -> Departamento
            // 4 -> Id Max Signal
            inventario = CittusInventario()
            inventario!!.IdInventario = data[0].toInt()
            inventario!!.IdMunicipio = data[2]
            Log.e("INVENTARIO", inventario.toString())
        }
        // Make Item of list
        if (inventario != null && listSignal == null) {
            // Init List
            listSignal = CittusListSignal()
            listSignal!!.IdInventario = inventario!!.getInventarioID()
            listSignal!!.setInventario(inventario!!)
            listSignal!!.IdSignal = data[1].toInt()
            Log.e("ListaSenal", listSignal.toString())
            // Set ID Max (Signal)
            if(contBaseID==0)
            contBaseID = data[4].toInt() // 4 - > Get ID Max
        }

    }

    private fun saveAllElements(view: View) {
        if (inventario != null && listSignal != null) {
            // Make Object Main



            signalMain = CittusSignal(maxID.toInt())

            // Get All Data
            getAllData()
            // Upload to Data Base
            uploadDataBase()
        } else {
            message = "Error faltal, no se pudo crear el inventario"
        }
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun getAllData() {
        try {
            // Get Data Main
            // TODO: ISV DATA MAIN (1)
            // 0 -> Latitud
            // 1 -> Longitud
            // 2 -> Clasification
            // 3 -> Img Front
            // 4 -> Img Front
            // 5 -> Img Front
            // ISV
            var isvMain = tabMain.getData()
            signalMain!!.Latitud = isvMain[0].toFloat() // 0 -> Latitud
            signalMain!!.Longitud = isvMain[1].toFloat() // 1 -> Longitud
            signalMain!!.PhotoFront = Uri.parse(isvMain[3]) // 3 -> Img Front
            // Type
            when (isvMain[2]) {
                "Horizontal" -> {
                // Get Data - HV
                // TODO: ISV Horizontal DATA MAIN
                // 1-0 -> Direccion
                // 1-1 -> Location Trayecto
                // 1-2 -> Carril
                // 1-3 -> Porcentaje
                var horizontalData = ArrayList<String>()//= tabMain.getDataHorizontal()
                    //if(horizontalData == null || horizontalData.size==0 || horizontalData.isEmpty()) { }
                    horizontalData = horizontalItems
                    if(horizontalData!=null) {
                        signalMain!!.Direccion = horizontalData[0] // 1-0 -> Direccion
                        signalMain!!.Location = horizontalData[1] // 1-1 -> Location Trayect
                        signalMain!!.Carril = horizontalData[2] // 1-2 -> Carril
                        signalMain!!.Porcentaje = horizontalData[3] // 1-3 -> Porcentaje

                        // Clasification or Name
                        signalMain!!.TypeSignal = isvMain[2] // 2 -> Clasificacion
                    }

                }
                "Vertical" -> {
                    signalMain!!.PhotoBack = isvMain[4] // 3 -> Img Back
                    signalMain!!.PhotoPlaque = isvMain[5] // 3 -> Img Plaque

                    // Clasificacion or Name
                    signalMain!!.TypeSignal = verticalItems // ? -> Clasificacion

                    // INFORMATION
                    // TODO: Get Data Main - Information
                    // 0 -> LocationMain
                    // 1 -> Size
                    // 2 -> Signal
                    // 3 -> Starts - State Post
                    var tempArrayInformation: ArrayList<String> = tabInformation.getData()
                    signalMain!!.LocationMain = tempArrayInformation[0] // 0 -> Norte - Sur, Este - Oeste
                    signalMain!!.Size = tempArrayInformation[1] // 1 -> 90 x 90
                    signalMain!!.TipoFijacion = tempArrayInformation[2] // 2 -> Signal:Poste de Luz
                    signalMain!!.EstadoFijacion = tempArrayInformation[3] // 3 -> Starts
                }
                else->{
                    exceptionMain = true
                }
            }
            // Get Info Imagen Select
            var dataImagen = dataImagenes
            // TODO: Get Data Images
            // 0 -> Number
            // 1 -> Code
            // 2 -> Img Select
            if(dataImagen!=null){
                //signalMain!!.Codigo = dataImagen[0] // 1 - 1 - 0 -> Number
                signalMain!!.Codigo = dataImagen[1] // 1 - 1 - 0 -> Code
                signalMain!!.Simbolo = dataImagen[2] // 1 - 1 - 0 -> Img - Symbol
            }

            // GENERAL DATA
            // TODO: Get Data Main - General Data
            // 0 -> LocationBetween 1
            // 1 -> LocationBetween 2
            // 2 -> LocationBetween 3
            // 3 -> Starts 2 - State Signal
            var tempArrayGeneralData: ArrayList<String> =tabGeneralData.getData()
            signalMain!!.LocationBetween_1 = tempArrayGeneralData[0] // 3 -> Starts 2 - State Signal
            signalMain!!.LocationBetween_2 = tempArrayGeneralData[1] // 3 -> Starts 2 - State Signal
            signalMain!!.LocationBetween_3 = tempArrayGeneralData[2] // 3 -> Starts 2 - State Signal
            signalMain!!.Estado = tempArrayGeneralData[3] // 3 -> Starts 2 - State Signal

            // Show Data
            if (signalMain != null) {
                Log.i("DataBase", signalMain.toString())
                exceptionMain = false
            }

        } catch (e: Exception) {
            var errorMain = when {
                e.message.equals(
                    "lateinit property takePictureFront has not been initialized",
                    true
                ) -> "NO ha tomado una fotografia Principal de la señal"
                e.message.equals(
                    "lateinit property takePictureBack has not been initialized",
                    true
                ) -> "NO ha tomado una fotografia Posterior(Atras) de la señal"
                e.message.equals(
                    "lateinit property takePicturePlaque has not been initialized",
                    true
                ) -> "NO ha tomado una fotografia de la Placa en la señal"
                else -> e.message
            }
            Toast.makeText(this, "$errorMain", Toast.LENGTH_SHORT).show()
            exceptionMain = true

        }
    }

    private fun uploadDataBase() {
        if (signalMain != null && exceptionMain === false) {
            // Set Dates
            signalMainArray.add(signalMain!!)
            listSignal!!.setSignalMain(signalMainArray!!)

            val bd = DAOConnection(this)
            if (bd.addSignal(listSignal!!) === true) {
                message = "Datos añadidos con exito."
                // Upload Images
                uploadImages()
                // Reset Views
                val intent = intent
                intent.putExtra("contBaseID",contBaseID)
                finish()
                startActivityForResult(intent,ActionsRequest.GET_ID)
            } else {
                message = "No se han podido subir los datos, reviselos por favor."
            }
        } else {
            message = "No se puede crear la señal, revise los datos por favor."
        }
    }

    private fun uploadImages(){
        var makeToUpoad:MakeToUpoad = MakeToUpoad(this);

        makeToUpoad.showFileChooser(signalMain!!.PhotoFront)

    }

    private fun resetData(){
        tabMain = TabMain(mainActivity)
        tabInformation = TabInformation(mainActivity)
        tabGeneralData = TabGeneralData(mainActivity)
    }

}
