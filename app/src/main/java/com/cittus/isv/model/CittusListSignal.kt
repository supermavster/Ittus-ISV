package com.cittus.isv.model

import android.os.Parcel
import android.os.Parcelable

/*
// Main Class
class CittusListSignal  {

    // Check Login
    var login: Boolean? = null
    // Inventario Main
    //var municipality: Municipalities? = null

    // Signals
    //var signal: ArrayList<CittusISV>? = null
    // Add ID by Signal
    //var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null



}
*/

data class CittusListSignal(val login: Int = 0) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CittusListSignal> = object : Parcelable.Creator<CittusListSignal> {
            override fun newArray(size: Int): Array<CittusListSignal?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): CittusListSignal = CittusListSignal(source)
        }

    }

    constructor(source: Parcel) : this(source.readInt())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeInt(login)
        }
    }

    override fun describeContents(): Int = 0
}
