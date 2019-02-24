package com.cittus.isv.view


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import com.cittus.isv.model.*
import com.cittus.isv.view.tabs.TabGeneralData
import com.cittus.isv.view.tabs.TabInformation
import com.cittus.isv.view.tabs.TabMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    var mainActivity: Activity = this

    // Make Clases
    private var inventario: CittusInventario? = null
    private var listSignal: CittusListSignal? = null
    private var signalMain: CittusSignal? = null
    var message = "";

    // Variables Table
    private var tabMain: TabMain = TabMain(mainActivity)
    private var tabInformation: TabInformation = TabInformation(mainActivity)
    private var tabGeneralData: TabGeneralData = TabGeneralData(mainActivity)

    // Exception
    var exceptionMain: Boolean = false

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //TODO: Get Main DATA - INVENTARIO
        getAndSetDataMain()

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
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


    // Camera Action // Return Intend Action
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i("Main Activity",requestCode.toString()+"->"+resultCode+"->"+data.toString())
        when (requestCode) {
            ActionsRequest.TAKE_PHOTO_REQUEST and RESULT_OK-> tabMain.processCapturedPhoto(tabMain.getTakePictureMain())
            ActionsRequest.GET_HORIZONTAL_VALUES or ActionsRequest.GET_IMAGES -> {
                if (data != null){
                    val extras = data!!.extras ?: return
                    val myString = extras.getStringArrayList("getData")
                    Log.i("getData", "getData:$myString")
                }
            }

            else->{
                    super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun getAndSetDataMain(){
        val data = intent.getStringArrayListExtra("getData")
        // Init Inventario
        if(data!=null && inventario == null){
            // TODO: Location DATA MAIN (2)
            // 0 -> Id Inventario
            // 1 -> Id Lista Senal
            // 2 -> Municipio
            // 3 -> Departamento
            inventario = CittusInventario()
            inventario!!.IdInventario = data[0].toInt()
            inventario!!.IdMunicipio = data[2]
            Log.e("INVENTARIO",inventario.toString())
        }
        // Make Item of list
        if(inventario != null && listSignal == null) {
            // Init List
            listSignal = CittusListSignal()
            listSignal!!.IdInventario = inventario!!.getInventarioID()
            listSignal!!.IdSignal = data[1].toInt()
            Log.e("ListaSenal", listSignal.toString())
        }
    }

    private  fun saveAllElements(view: View) {
        if(inventario!=null && listSignal != null) {
            // Make Object Main
            signalMain = CittusSignal()
            // Get All Data
            getAllData()
            // Upload to Data Base
            uploadDataBase()
        }else{
            message = "Error faltal, no se pudo crear el inventario"
        }
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun getAllData(){
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
            signalMain!!.TypeSignal = isvMain[2] // 2 -> Clasificacion
            signalMain!!.PhotoFront = isvMain[3] // 3 -> Img Front

            if(isvMain[2].equals("Horizontal",true)) {

/*            if(isvMain[2].equals("Horizontal",true)) {
    // Get Data - HV
    // TODO: ISV Horizontal DATA MAIN
    // 1-0 -> Direccion
    // 1-1 -> Location Trayecto
    // 1-2 -> Carril
    // 1-3 -> Porcentaje
    signalMain!!.Direccion = isvMain[10] // 10 -> Direccion
    signalMain!!.Location = isvMain[11] // 11 -> Location Trayect
    signalMain!!.Carril = isvMain[12] // 12 -> Carril
    signalMain!!.Porcentaje = isvMain[13] // 13 -> Porcentaje
    */
}else if(isvMain[2].equals("Vertical",true)) {
                signalMain!!.PhotoBack = isvMain[4] // 3 -> Img Back
                signalMain!!.PhotoPlaque = isvMain[5] // 3 -> Img Plaque

}
            /*
// INFORMATION
// TODO: Get Data Main - Information
// 0 -> LocationMain
// 1 -> Size
// 2 -> Signal
// 3 -> Starts - State Post
var tempArrayInformation: ArrayList<String> = tabInformation.getData()
signalMain!!.LocationMain = tempArrayInformation[0] // 0 -> Norte - Sur, Este - Oeste
signalMain!!.Size = tempArrayInformation[1] // 1 -> 90 x 90
signalMain!!.TipoFijacion = isvMain[2] // 2 -> Signal:Poste de Luz
signalMain!!.EstadoFijacion = isvMain[3] // 3 -> Starts

// GENERAL DATA
// TODO: Get Data Main - General Data
// 0 -> LocationBetween 1
// 1 -> LocationBetween 2
// 2 -> LocationBetween 3
// 3 -> Starts 2 - State Signal
var tempArrayGeneralData: ArrayList<String> =tabGeneralData.getData()
signalMain!!.LocationBetween_1 = tempArrayInformation[0] // 3 -> Starts 2 - State Signal
signalMain!!.LocationBetween_2 = tempArrayInformation[1] // 3 -> Starts 2 - State Signal
signalMain!!.LocationBetween_3 = tempArrayInformation[2] // 3 -> Starts 2 - State Signal
signalMain!!.Estado = tempArrayInformation[3] // 3 -> Starts 2 - State Signal
*/
// Show Data
if(signalMain!=null) {
    Log.i("DataBase", signalMain.toString())
    exceptionMain = false
}

} catch (e: Exception) {
Log.e("NULL", e.message)
var errorMain = when {
    e.message.equals(
        "lateinit property takePictureFront has not been initialized",
        true
    )
    -> "NO ha tomado una fotografia Principal de la señal"
    else ->e.message
}
Toast.makeText(this, "$errorMain  para poder continuar", Toast.LENGTH_SHORT).show()
exceptionMain = true

}
}

private fun uploadDataBase() {
if(signalMain!=null && exceptionMain === false) {
    val bd = DAOConnection(this)
    if(bd.addSignal(signalMain!!)===true) {
        message = "Datos añadidos con exito."
        // Reset Views
        tabMain = TabMain(mainActivity)
        tabInformation = TabInformation(mainActivity)
        tabGeneralData = TabGeneralData(mainActivity)
        setContentView(R.layout.activity_main)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


    }else{
        message = "No se han podido subir los datos, reviselos por favor."
    }
}else{
    message = "No se puede crear la señal, revise los datos por favor."
}
}


}
