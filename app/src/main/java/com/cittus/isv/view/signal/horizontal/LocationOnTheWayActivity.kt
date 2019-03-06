package com.cittus.isv.view.signal.horizontal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.cittus.isv.R
import com.cittus.isv.controller.MainImage
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.EndPoints
import kotlinx.android.synthetic.main.activity_location_on_the_way.*

class LocationOnTheWayActivity : Fragment() {

    private var viewMain: View? = null;
    // Variables
    var carril = "";
    var porcentaje = "";
    // Exception
    var exceptionMain: Boolean = false
    var elementsBaseImage = java.util.ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_location_on_the_way, container, false)
        viewMain = view
        // Init Process
        initProcess()
        return view
    }

    private fun initProcess(){

        makeActionsButtons()
    }


    var main = false
    private fun makeActionsButtons() {

        viewMain!!.findViewById<Button>(R.id.btn_intersection).setOnClickListener {view->
            // Block Other Button
            viewMain!!.findViewById<Button>(R.id.btn_intersection).setLinkTextColor(android.R.attr.colorPrimary)
            viewMain!!.findViewById<Button>(R.id.btn_stretch).setLinkTextColor(Color.RED)
            viewMain!!.findViewById<Button>(R.id.btn_stretch).isClickable = main

            // Make Images
            if (!viewMain!!.findViewById<Button>(R.id.btn_stretch).isClickable) {
                makeActivityImages(R.string.title_horizontal_intersection, EndPoints.URL_GET_HORIZONTAL_INTERSECTION)
                // TODO: Revisar
                Navigation.findNavController(view).navigate(R.id.addressLocationOnTheWayActivity)
            }
            main = !main
        }
        viewMain!!.findViewById<Button>(R.id.btn_stretch).setOnClickListener {view->
            // Block Other Button
            viewMain!!.findViewById<Button>(R.id.btn_stretch).setLinkTextColor(android.R.attr.colorPrimary)
            viewMain!!.findViewById<Button>(R.id.btn_intersection).setLinkTextColor(Color.RED)
            viewMain!!.findViewById<Button>(R.id.btn_intersection).isClickable = main

            // Make Images
            if (!viewMain!!.findViewById<Button>(R.id.btn_intersection).isClickable) {
                makeActivityImages(R.string.title_horizontal_stretch, EndPoints.URL_GET_HORIZONTAL_STRETCH)
                // TODO: Revisar
                Navigation.findNavController(view).navigate(R.id.addressLocationOnTheWayActivity)
            }
            main = !main
        }
    }

    fun getDataImagenSelected(): ArrayList<String>{
        return elementsBaseImage
    }

    lateinit var intentImage: Intent
    private fun makeActivityImages(title: Int, url_img: String, code: Boolean = true, description: Boolean = true) {
        intentImage = Intent(context, MainImage::class.java)
        if (intentImage != null) {
            intentImage.putExtra("title", resources.getString(title))
            intentImage.putExtra("url_img", url_img)
            intentImage.putExtra("code", code)
            intentImage.putExtra("description", description)
            intentImage.putExtra("Action",ActionsRequest.GET_HORIZONTAL_IMAGES_VALUES)
            startActivityForResult(intentImage, ActionsRequest.GET_HORIZONTAL_IMAGES_VALUES)

        }
    }


}