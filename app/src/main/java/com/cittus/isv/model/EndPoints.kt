package com.cittus.isv.model

object EndPoints {
    // Server
    private const val SERVER_MAIN = "192.168.43.44"//"192.168.43.137"//"172.20.1.13"//
    private const val PATH_MAIN = "http://${SERVER_MAIN}/ittus-senalesviales/queries/"

    // Upload Photo
    const val SERVER_URL = "${PATH_MAIN}UploadFiles.php"

    // Main Get
    private const val URL_ROOT = "${PATH_MAIN}sliderjsonoutput.php"
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

    // Get Main Init
    const val URL_GET_DEPARTAMENTOS = "$URL_ROOT?departamentos=true"
    const val URL_GET_MUNICIPIOS = "$URL_ROOT?municipio"
    const val URL_GET_SEARCH_MUNICIPIOS = "$URL_ROOT?municipio="


    // Set Data
    private const val URL_ADD_MAIN = "$URL_ROOT?add="
    const val URL_ADD_SIGNAL = URL_ADD_MAIN + "signal"

    // Get Max ID
    const val URL_GET_MAX_ID = "$URL_ROOT?maxID="

    // Get Folder Main Images
    const val IMAGE_DIRECTORY_NAME = "ITTUS"

    const val URL_GET_MAX_ID_INVENTORY = URL_GET_MAX_ID + "inventario"
    const val URL_GET_MAX_ID_LIST = URL_GET_MAX_ID + "lista"
    const val URL_GET_MAX_ID_SIGNAL = URL_GET_MAX_ID + "signal"
}
