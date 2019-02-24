package com.cittus.isv.model

object EndPoints {
    // Server
    private const val SERVER_MAIN =  "192.168.43.44"//"192.168.43.137"//"172.20.1.13"

    // Main Get
    private const val URL_ROOT = "http://$SERVER_MAIN/ittus-senalesviales/queries/sliderjsonoutput.php"
    private const val URL_GET_MAIN = "$URL_ROOT?signal="

    // Get Data JSON
    const val URL_GET_VERTICAL_INFO_SERVICES = URL_GET_MAIN + "info&from=SI-07"
    const val URL_GET_VERTICAL_INFO_TOURIST = URL_GET_MAIN + "turis"
    const val URL_GET_VERTICAL_INFO_LOCATION = URL_GET_MAIN + "info&from=SI-01&to=SI-06"
    const val URL_GET_VERTICAL_REGULATORY = URL_GET_MAIN + "regla"
    const val URL_GET_VERTICAL_PREVENTIVES = URL_GET_MAIN + "prev"
    const val URL_GET_VERTICAL_WORK = URL_GET_MAIN + "obra"
    const val URL_GET_VERTICAL_CYCLE_ROUTE = URL_GET_MAIN + "cicl"
    const val URL_GET_HORIZONTAL_STRETCH = URL_GET_MAIN + "tramo"
    const val URL_GET_HORIZONTAL_INTERSECTION = URL_GET_MAIN + "inter"

    // Set Data
    private const val URL_ADD_MAIN = "$URL_ROOT?add="

    const val URL_ADD_SIGNAL = URL_ADD_MAIN + "signal"
}
