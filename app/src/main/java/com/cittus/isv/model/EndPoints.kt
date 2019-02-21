package com.cittus.isv.model

object EndPoints {
    val SERVER_MAIN = "192.168.0.3"//"172.20.1.13"
    private val URL_ROOT = "http://"+SERVER_MAIN+"/ittus-senalesviales/queries/sliderjsonoutput.php?signal="
    val URL_ADD_ARTIST = URL_ROOT + "addartist"

    // Intersection
    val URL_GET_INTERSECTION = URL_ROOT + "interpng"
    val URL_GET_STRETCH = URL_ROOT + "tramopng"

    //
    val URL_GET_TOURIST = URL_ROOT + "turis"

}
