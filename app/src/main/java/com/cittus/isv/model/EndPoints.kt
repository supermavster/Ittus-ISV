package com.cittus.isv.model

object EndPoints {
    // Server
    private const val SERVER_MAIN =  "192.168.43.44"//"192.168.43.137"//"172.20.1.13"

    // Main Get
    private const val URL_ROOT = "http://$SERVER_MAIN/ittus-senalesviales/queries/sliderjsonoutput.php?signal="

    // Get Data JSON
    const val URL_GET_VERTICAL_INFO_SERVICES = URL_ROOT + "serv"
    const val URL_GET_VERTICAL_INFO_TOURIST = URL_ROOT + "turis"
    const val URL_GET_VERTICAL_INFO_LOCATION = URL_ROOT + "info"
    const val URL_GET_VERTICAL_REGULATORY = URL_ROOT + "regla"
    const val URL_GET_VERTICAL_PREVENTIVES = URL_ROOT + "prev"
    const val URL_GET_VERTICAL_WORK = URL_ROOT + "obra"
    const val URL_GET_VERTICAL_CYCLE_ROUTE = URL_ROOT + "cicl"
    const val URL_GET_HORIZONTAL_STRETCH = URL_ROOT + "tramo"
    const val URL_GET_HORIZONTAL_INTERSECTION = URL_ROOT + "inter"

    // Set Data
    private const val URL_ROOT_ADD = "http://$SERVER_MAIN/ittus-senalesviales/queries/sliderjsonoutput.php?signal="
    const val URL_ADD_SIGNAL = ""
}
