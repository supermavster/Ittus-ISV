package com.cittus.isv.DAO

import android.app.Activity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.cittus.isv.model.EndPoints
import org.json.JSONException
import org.json.JSONObject

class DAOConnection(mainActivity: Activity) {
    // Global Variables
    // Get Main Activity (To show Elements or Call)
    private var mainActivity = mainActivity
    private var arrayTemp: ArrayList<String> = ArrayList()

    //adding a new record to database
    private fun addArtist() {
        //getting the record values
        val name = "editTextArtistName?.text.toString()"
        val genre = "spinnerGenre?.selectedItem.toString()"

        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_ADD_SIGNAL,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(mainActivity, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    //Toast.makeText(mainActivity, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("name", name)
                params.put("genre", genre)
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}