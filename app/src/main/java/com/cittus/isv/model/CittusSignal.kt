package com.cittus.isv.model

class CittusSignal {

    var altitude: Float = 0.0f
    var latitude: Float = 0.0f
    var longitude: Float = 0.0f

    // Fotos
    var photoFront: String = ""
    var photoBack: String = ""
    var photoPlaque: String = ""

    var location: String? = null

    override fun toString(): String {
        return "CittusSignal(altitude=$altitude, latitud=$latitude, longitud=$longitude, photoFront='$photoFront', photoBack='$photoBack', photoPlaque='$photoPlaque', Location=$location)"
    }

}