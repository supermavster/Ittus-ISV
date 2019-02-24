package com.cittus.isv.DAO

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cittus.isv.complements.slider.CustomVolleyRequest
import com.cittus.isv.model.CittusSignal
import com.cittus.isv.model.EndPoints
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DAOConnection(mainActivity: Activity) {
    // Global Variables
    // Get Main Activity (To show Elements or Call)
    private var mainActivity = mainActivity
    private var arrayTemp: ArrayList<String> = ArrayList()

    fun loadElements(url:String):ArrayList<String> {
        var elements = ArrayList<String>();
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            Response.Listener<String> { s ->
                try {
                    var elementsJSON = JSONArray(s);
                    Log.e("JSON", elementsJSON.toString())
                    for (i in 0 until elementsJSON.length()) {
                        elements.add(elementsJSON.get(i).toString())
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(mainActivity, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(mainActivity)
        requestQueue.add<String>(stringRequest)
        return elements;
    }
    var elements = ""
    fun loadElement(url:String):String {
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            Response.Listener<String> { s ->
                if(s.isNotEmpty())elements = s.toString()
            }, Response.ErrorListener { volleyError -> Toast.makeText(mainActivity, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(mainActivity)
        requestQueue.add<String>(stringRequest)
        return elements
    }
    var check = false;

    //adding a new record to database
        public fun addSignal(signalMain: CittusSignal):Boolean {
        check = false
        //getting the record values
        val name = signalMain.Latitud.toString()
        val genre = signalMain.Longitud.toString()

        Log.e("INIT",EndPoints.URL_ADD_SIGNAL)
        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_ADD_SIGNAL,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(mainActivity, obj.getString("message"), Toast.LENGTH_LONG).show()
                    Log.e("Result","DONE")
                    check = true
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("Error",e.message)
                    check = false
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Log.e("Error_2",volleyError.message)
                    Toast.makeText(mainActivity, volleyError.message, Toast.LENGTH_LONG).show()
                    check = false
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("name", name)
                params.put("genre", genre)
                Log.e("Parametes",params.toString())
                return params
            }
        }
        Log.i("Make",stringRequest.toString())
        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
        CustomVolleyRequest.getInstance(mainActivity).addToRequestQueue(stringRequest);
        return check
    }
}