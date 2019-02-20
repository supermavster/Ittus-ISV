package com.cittus.isv.model

object EndPoints {
    val SERVER_MAIN = "172.20.1.13"
    private val URL_ROOT = "http://"+SERVER_MAIN+"/senalesviales/sliderjsonoutput.php"
    val URL_ADD_ARTIST = URL_ROOT + "addartist"
    val URL_GET_ARTIST = URL_ROOT + "getartists"
}