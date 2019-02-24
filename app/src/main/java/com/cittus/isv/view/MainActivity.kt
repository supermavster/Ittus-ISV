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


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //TODO: Get Main DATA - INVENTARIO
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
        if(inventario != null) {
            // Init List
            listSignal = CittusListSignal()
            listSignal!!.IdInventario = inventario!!.IdInventario
            listSignal!!.IdSignal = data[1].toInt()
            Log.e("ListaSenal", listSignal.toString())
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            if(inventario!=null && listSignal != null){
                // Make Object Main
                signalMain = CittusSignal()

                // Get Data Main
                // TODO: ISV DATA MAIN (1)
                // 0 -> Latitud
                // 1 -> Longitud
                // 2 -> Clasification
                // 3 -> Img Front
                // TODO: ISV Horizontal DATA MAIN
                // 10 -> Direccion
                // 11 -> Location Trayecto
                // 12 -> Carril
                // 13 -> Porcentaje
                // ISV
                var isvMain = tabMain.getData()
                signalMain!!.Latitud = isvMain[0].toFloat() // 0 -> Latitud
                signalMain!!.Longitud = isvMain[1].toFloat() // 1 -> Longitud
                signalMain!!.TypeSignal = isvMain[2] // 2 -> Clasificacion
                //signalMain!!.TypeSignal = isvMain[3] // 3 -> Img Front

                signalMain!!.Direccion = isvMain[10] // 10 -> Direccion
                signalMain!!.Location = isvMain[11] // 11 -> Location Trayect

                try {
                    var tempArray: ArrayList<String> = ArrayList<String>()


                    // INFORMATION
                    tempArray.addAll(tabInformation.getData())

                    // General Data
                    tempArray.addAll(tabGeneralData.getData())


                    if(tempArray!=null)
                        Log.i("DataBase", tempArray.toString())


                } catch (exc: Exception) {
                    Log.e("ERROR", exc.message)
                }


                if(signalMain!=null) {
                    val bd = DAOConnection(this)
                    if(bd.addSignal(signalMain!!)===true) {
                        message = "Datos añadidos con exito."
                    }else{
                        message = "No se han podido subir los datos, reviselos por favor."
                    }
                }else{
                    message = "No se puede crear la señal, revise los datos por favor."
                }
            }else{
                message = "Error faltal, no se pudo crear el inventario"
            }
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            /*val registro = ContentValues()
            registro.put("codigo", et1.getText().toString())
            registro.put("descripcion", et2.getText().toString())
            registro.put("precio", et3.getText().toString())
            bd.insert("articulos", null, registro)*/
            //var sortedList: ArrayList<String> = DAOConnection(this).getElementsJSON("http://192.168.0.3/ittus-senalesviales/queries/sliderjsonoutput.php?signal=turis","code")
            /*
*/
            //bd.close()
            // Add Data TO DB
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



}
