package com.cittus.isv.DAO

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cittus.isv.complements.slider.CustomVolleyRequest
import com.cittus.isv.model.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DAOConnection(contextMain: Context) {

    // Main Variable
    private var contextMain: Context = contextMain

    // Data from GETs
    var check = true;
    var element = ""

    fun getData(url: String): ArrayList<String> {
        var inside = 0
        var elements = ArrayList<String>()
        val stringRequest = StringRequest(
            Request.Method.GET, // Method GET
            url,    // Url JSON
            Response.Listener<String> { s ->
                try {
                    // Check if Values Get is not Empty
                    if (s.isNotEmpty() && inside == 0) {
                        inside++
                        // Get Elements by URL and make the JSON - Array
                        var elementsJSON = JSONArray(s);
                        // Add elements to array (Formatted JSON)
                        for (i in 0 until elementsJSON.length()) {
                            elements.add(elementsJSON.get(i).toString())
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { volleyError ->
                Log.e("Error", volleyError.message)
            })

        val requestQueue = Volley.newRequestQueue(contextMain)
        requestQueue.add<String>(stringRequest)
        return elements
    }

    fun getDataSingle(url: String): String {
        var inside = 0
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            Response.Listener<String> { s ->
                if (s.isNotEmpty() && inside == 0) {
                    element = ""
                    inside++
                    element = s.toString()
                }
            }, Response.ErrorListener { volleyError ->
                Log.e("Error", volleyError.message)
            })

        val requestQueue = Volley.newRequestQueue(contextMain)
        requestQueue.add<String>(stringRequest)
        return element
    }

    //adding a new record to database
    public fun addSignal(signalMain: CittusISV): Boolean {

        // Get Data
        var typeSignal: String
        var imagesByCode: ImagenSignalCode? = null
        var locationSignal: LocationSignal? = null
        var cittusSignal: CittusSignal? = null
        var verticalSignal: VerticalSignal? = null
        var horizontalSignal: HorizontalSignal? = null

        Log.e("INIT", EndPoints.URL_ADD_SIGNAL)
        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_ADD_SIGNAL,
            Response.Listener<String> { response ->
                Log.e("Error JSON", response)
                check = true
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(contextMain, obj.getString("message"), Toast.LENGTH_LONG).show()
                    Log.e("Result", "DONE")
                    check = true
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("Error", e.message)
                    check = false
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Log.e("Error_2", volleyError.message)
                    Toast.makeText(contextMain, volleyError.message, Toast.LENGTH_LONG).show()
                    check = false
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("Signal", signalMain.toString())
                Log.e("Parametes", params.toString())
                check = true

                return params
            }
        }
        Log.i("Make", stringRequest.toString())
        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
        CustomVolleyRequest.getInstance(contextMain).addToRequestQueue(stringRequest);
        return check
    }


}