package com.cittus.isv.DAO

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class DAOConnection(contextMain: Context) {

    // Main Variable
    private var contextMain: Context = contextMain

    // Data from GETs
            ;
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

        Log.e("Data", elements.toString())

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
        Log.e("eleme", element)
        return element
    }


}