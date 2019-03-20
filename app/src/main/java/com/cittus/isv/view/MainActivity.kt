package com.cittus.isv.view


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.cittus.isv.DAO.DAOConnection
import com.cittus.isv.R
import com.cittus.isv.complements.Permissions
import com.cittus.isv.complements.camera.TakePicture
import com.cittus.isv.controller.base.GeolocationActivity
import com.cittus.isv.controller.signal.PhotoGPSActivity
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.CittusListSignal
import com.cittus.isv.model.CittusSignal
import com.cittus.isv.model.Municipalities
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Main Variables
    var mainActivity: Activity = this
    var connection : DAOConnection = DAOConnection(this)
    var maxID:String = ""
    override fun onSupportNavigateUp() =
        findNavController(my_nav_host_fragment).navigateUp()


    // Make Clases
    private var inventario: Municipalities? = null // inventario
    private var listSignal: CittusListSignal? = null
    private var signalMain: CittusSignal? = null
    var message = ""
    private var signalMainArray: ArrayList<CittusSignal> = ArrayList<CittusSignal>()
    var contBaseID = 0

    // Exception
    var exceptionMain: Boolean = false

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Permissions
        Permissions(this, false).setPermissions()
        // Init Process (All Actions in the tab MAIN)
        initProcess()
    }


    private fun initProcess(){
        //TODO: Get Main DATA - INVENTARIO
        //getAndSetDataMain()
    }

    var horizontalItems = ArrayList<String>()
    var verticalItems:String=""
    var dataImagenes = ArrayList<String>()

    // Camera Action // Return Intend Action
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // Show Action And Values (Send Variables) - Intent
        Log.e("Main Activity", requestCode.toString() + "->" + resultCode + "->" + data.toString())

        // Get Fragment Called
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        // Select Case
        when (requestCode) {
            ActionsRequest.TAKE_PHOTO_REQUEST_NORTH and RESULT_OK -> {
                val fragment = navHostFragment!!.childFragmentManager.fragments[0] as GeolocationActivity
                // Call Method Fragment
                processCapturedPhoto(fragment.getTakePictureMainNorth())
            }
            ActionsRequest.TAKE_PHOTO_REQUEST_SOUTH and RESULT_OK -> {
                val fragment = navHostFragment!!.childFragmentManager.fragments[0] as GeolocationActivity
                // Call Method Fragment
                processCapturedPhoto(fragment.getTakePictureMainSouth())
            }
            ActionsRequest.TAKE_PHOTO_REQUEST_WEST and RESULT_OK -> {
                val fragment = navHostFragment!!.childFragmentManager.fragments[0] as GeolocationActivity
                // Call Method Fragment
                processCapturedPhoto(fragment.getTakePictureMainWest())
            }
            ActionsRequest.TAKE_PHOTO_REQUEST_EAST and RESULT_OK -> {
                val fragment = navHostFragment!!.childFragmentManager.fragments[0] as GeolocationActivity
                // Call Method Fragment
                processCapturedPhoto(fragment.getTakePictureMainEast())
            }
            ActionsRequest.TAKE_PHOTO_REQUEST_FRONT and RESULT_OK -> {
                val fragment = navHostFragment!!.childFragmentManager.fragments[0] as PhotoGPSActivity
                // Call Method Fragment
                processCapturedPhoto(fragment.getTakePictureMainFront())
            }
            ActionsRequest.TAKE_PHOTO_REQUEST_BACK and RESULT_OK -> {
                val fragment = navHostFragment!!.childFragmentManager.fragments[0] as PhotoGPSActivity
                // Call Method Fragment
                processCapturedPhoto(fragment.getTakePictureMainBack())
            }
            ActionsRequest.TAKE_PHOTO_REQUEST_PLAQUE and RESULT_OK -> {
                val fragment = navHostFragment!!.childFragmentManager.fragments[0] as PhotoGPSActivity
                // Call Method Fragment
                processCapturedPhoto(fragment.getTakePictureMainPlaque())
            }
            ActionsRequest.GET_HORIZONTAL_VALUES -> {
                if (data != null) {
                    val extras = data.extras ?: return
                    horizontalItems = extras.getStringArrayList("getData")
                    dataImagenes = extras.getStringArrayList("getDataImages")
                    Log.e("getData Horizontal", "getData:$horizontalItems  Images:$dataImagenes")
                }
            }
            ActionsRequest.GET_VERTICAL_VALUES or ActionsRequest.GET_VERTICAL_IMAGES_VALUES->{
                if (data != null) {
                    val extras = data.extras ?: return
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
                val extras = data.extras ?: return
                contBaseID = extras.getInt("contBaseID")
                Log.e("Show ID",contBaseID.toString())
            }
            ActionsRequest.GET_INIT->{
                Log.e("Show GET_INIT","Yes")
            }
            // Save saveAllElements(view)
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun processCapturedPhoto(takePicture: TakePicture) {
        takePicture.processCapturedPhoto(takePicture.getPath())
    }
}