package com.cittus.isv.view.horizontal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cittus.isv.DAO.DAOConnection
import com.cittus.isv.R
import com.cittus.isv.controller.MainImage
import com.cittus.isv.model.EndPoints
import kotlinx.android.synthetic.main.activity_horizontal_main.*

class ActivityHorizontalMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_main)

        initProcess()
    }

    private fun initProcess(){

        // Ubicacion Elementos
        locationWay()

        btn_save_ths.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun locationWay(){

        btn_intersection.setOnClickListener {
            btn_stretch.isClickable = !btn_intersection.isClickable
            makeActivityImages(R.string.title_horizontal_intersection,EndPoints.URL_GET_TOURIST,false)

        }

        btn_stretch.setOnClickListener {
            btn_intersection.isClickable = !btn_stretch.isClickable

            makeActivityImages(R.string.title_horizontal_stretch,"http://172.20.1.18/ittus-senalesviales/queries/sliderjsonoutput.php?signal=turis")
        }
    }

    private fun makeActivityImages(title: Int, url_img:String,code:Boolean=true,description: Boolean=true){
        var intent: Intent = Intent(this, MainImage::class.java)
        intent.putExtra("title", resources.getString(title))
        intent.putExtra("url_img",url_img)
        intent.putExtra("code",code)
        intent.putExtra("description",description)
        startActivity(intent)
    }
}
