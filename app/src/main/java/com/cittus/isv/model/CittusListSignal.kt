package com.cittus.isv.model

import android.os.Parcel
import android.os.Parcelable

data class CittusListSignal(
    val login: Int,
    var municipality: Municipalities?,
    var signal: ArrayList<CittusISV>?,
    var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>?
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CittusListSignal> = object : Parcelable.Creator<CittusListSignal> {
            override fun newArray(size: Int): Array<CittusListSignal?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): CittusListSignal = CittusListSignal(source)
        }

    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readParcelable(Municipalities::class.java.classLoader),
        arrayListOf<CittusISV>().apply {
            source.readArrayList(CittusISV::class.java.classLoader)
        },
        arrayListOf<GeolocationCardinalImages>().apply {
            source.readArrayList(GeolocationCardinalImages::class.java.classLoader)
        }
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeInt(login)
            // Data Municipality
            dest.writeParcelable(municipality, flags)
            // Cittus ISV - Signal
            var size: Int? = signal?.size
            if (size == null) {
                size = 0
            }
            for (i in 0 until size) {
                dest.writeParcelableArray(arrayOf(signal?.get(i)), flags)
            }
            // Cardinal Images
            size = geolocationCardinalImages?.size
            if (size == null) {
                size = 0
            }
            for (i in 0 until size) {
                dest.writeParcelableArray(arrayOf(geolocationCardinalImages?.get(i)), flags)
            }
        }
    }

    override fun describeContents(): Int = 0

    override fun toString(): String {
        return "[{\"CittusListSignal\":{\"login\":\"$login\",$municipality,\"CittusISV\":{$signal},\"GeolocationCardinalImages\":{$geolocationCardinalImages}}}]"
    }


}
