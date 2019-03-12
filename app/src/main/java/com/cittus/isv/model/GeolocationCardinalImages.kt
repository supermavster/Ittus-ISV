package com.cittus.isv.model


class GeolocationCardinalImages {

    var idPhotosGeolocalizationSignal = 0
    var photoNorth: String = ""
    var photoSouth: String = ""
    var photoWest: String = ""
    var photoEast: String = ""


    override fun toString(): String {
        return "GelocalizationImages(idPhotosGeolocalization=$idPhotosGeolocalizationSignal, photoNorth='$photoNorth', photoSouth='$photoSouth', photoWest='$photoWest', photoEast='$photoEast')"
    }


}
