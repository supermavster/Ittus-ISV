package com.cittus.isv.model

import android.os.Parcel
import android.os.Parcelable


data class GeolocationCardinalImages(
    var idPhotosGeolocalizationSignal: Int,
    var photoNorth: String,
    var photoSouth: String,
    var photoWest: String,
    var photoEast: String
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GeolocationCardinalImages> =
            object : Parcelable.Creator<GeolocationCardinalImages> {
                override fun newArray(size: Int): Array<GeolocationCardinalImages?> = arrayOfNulls(size)
                override fun createFromParcel(source: Parcel): GeolocationCardinalImages =
                    GeolocationCardinalImages(source)
            }

    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeInt(idPhotosGeolocalizationSignal)
            dest.writeString(photoNorth)
            dest.writeString(photoSouth)
            dest.writeString(photoWest)
            dest.writeString(photoEast)
        }
    }

    override fun describeContents(): Int = 0


    override fun toString(): String {
        return "GelocalizationImages(idPhotosGeolocalization=$idPhotosGeolocalizationSignal, photoNorth='$photoNorth', photoSouth='$photoSouth', photoWest='$photoWest', photoEast='$photoEast')"
    }


}
