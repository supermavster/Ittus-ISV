package com.cittus.isv.DAO

    import android.app.Activity
    import android.util.Log
    import android.widget.ListView
    import com.android.volley.AuthFailureError
    import com.android.volley.Request
    import com.android.volley.Response
    import com.android.volley.VolleyError
    import com.android.volley.toolbox.JsonArrayRequest
    import com.android.volley.toolbox.StringRequest
    import com.android.volley.toolbox.Volley
    import com.cittus.isv.model.EndPoints
    import org.json.JSONException
    import org.json.JSONObject

class DAOConnection (mainActivity: Activity) {
    // Global Variables
    // Get Main Activity (To show Elements or Call)
    private var mainActivity  = mainActivity
    private var arrayTemp:ArrayList<String> = ArrayList()


    public fun getElementsJSON(url: String, sql: String) {
        arrayTemp = ArrayList()
        // Instantiate the RequestQueue.
        // Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    arrayTemp.add(jsonObject.getString(sql))
            }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }, Response.ErrorListener { })
        // Add the request to the RequestQueue.
        val queue = Volley.newRequestQueue(mainActivity)
        queue.add(stringRequest)
    }


        //adding a new record to database
        private fun addArtist() {
            //getting the record values
            val name = "editTextArtistName?.text.toString()"
            val genre = "spinnerGenre?.selectedItem.toString()"

            //creating volley string request
            val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_ADD_ARTIST,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        //Toast.makeText(mainActivity, obj.getString("message"), Toast.LENGTH_LONG).show()
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