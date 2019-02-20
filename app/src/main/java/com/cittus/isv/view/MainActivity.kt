package com.cittus.isv.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.cittus.isv.DAO.DAOConnection


import com.cittus.isv.R
import com.cittus.isv.complements.Permissions
import com.cittus.isv.complements.gps.GetUserLocation
import com.cittus.isv.controller.MainImage
import com.cittus.isv.model.ActionsRequest.Companion.TAKE_PHOTO_REQUEST
import com.cittus.isv.view.tabs.TabGeneralData
import com.cittus.isv.view.tabs.TabInformation
import com.cittus.isv.view.tabs.TabMain
import com.cittus.isv.view.vertical.ActivityVerticalMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    var mainActivity:Activity = this

    // Variables Table
    private var tabMain: TabMain = TabMain(mainActivity)
    private var tabInformation:TabInformation = TabInformation(mainActivity)
    private var tabGeneralData:TabGeneralData = TabGeneralData(mainActivity)


     @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

         fab.setOnClickListener {
             //val bd = DAOConnection(this, "Ittus", null, 1).writableDatabase
             /*val registro = ContentValues()
             registro.put("codigo", et1.getText().toString())
             registro.put("descripcion", et2.getText().toString())
             registro.put("precio", et3.getText().toString())
             bd.insert("articulos", null, registro)*/
             startActivity(Intent(this, MainImage::class.java))
             //Log.i("DataBase",bd.isOpen.toString())
             //bd.close()
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

            return when(position) {
                0 ->tabMain
                1 ->tabInformation
                2 ->tabGeneralData
                else -> Fragment()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }


    // Camera Action
    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        if (resultCode == Activity.RESULT_OK
            && requestCode == TAKE_PHOTO_REQUEST) {
            tabMain.processCapturedPhoto(tabMain.getTakePictureMain())
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
