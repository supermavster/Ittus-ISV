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
import com.cittus.isv.model.ActionsRequest
import com.cittus.isv.model.ActionsRequest.Companion.GET_HORIZONTAL_VALUES
import com.cittus.isv.model.ActionsRequest.Companion.TAKE_PHOTO_REQUEST
import com.cittus.isv.view.tabs.TabGeneralData
import com.cittus.isv.view.tabs.TabInformation
import com.cittus.isv.view.tabs.TabMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    var mainActivity: Activity = this

    // Variables Table
    private var tabMain: TabMain = TabMain(mainActivity)
    private var tabInformation: TabInformation = TabInformation(mainActivity)
    private var tabGeneralData: TabGeneralData = TabGeneralData(mainActivity)


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val intent = intent

        val msg = intent.getStringArrayListExtra("getData")
        Log.e("Data",msg.toString())

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val bd = DAOConnection(this)
            bd.addArtist()
            /*val registro = ContentValues()
            registro.put("codigo", et1.getText().toString())
            registro.put("descripcion", et2.getText().toString())
            registro.put("precio", et3.getText().toString())
            bd.insert("articulos", null, registro)*/
            //var sortedList: ArrayList<String> = DAOConnection(this).getElementsJSON("http://192.168.0.3/ittus-senalesviales/queries/sliderjsonoutput.php?signal=turis","code")
            /*try {
                val intent = intent

                // Set Template New Activity
                Log.i("testx", intent.getStringExtra("hola"))


                var tempArray: ArrayList<String> = ArrayList<String>()
                // ISV
                tempArray.addAll(tabMain.getData())

                // INFORMATION
                tempArray.addAll(tabInformation.getData())

                // General Data
                tempArray.addAll(tabGeneralData.getData())


                if(tempArray!=null)
                Log.i("DataBase", tempArray.toString())


            } catch (exc: Exception) {
                Log.e("ERROR", exc.message)
            }
*/
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
            ActionsRequest.GET_INIT-> {
                if (data != null){
                    val extras = data!!.extras ?: return
                    val myString = extras.getStringArrayList("getData")
                    Log.i("getData", "getData:" + myString.toString())
                }
            }
            else->{
                    super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }



}
