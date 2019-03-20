package com.cittus.isv.model

import android.os.Parcel
import android.os.Parcelable

data class CittusISV(var count: Int, var typeSignal: String) : Parcelable {

    var imagesByCode: ImagenSignalCode? = null
    var locationSignal: LocationSignal? = null
    var cittusSignal: CittusSignal? = null
    var verticalSignal: VerticalSignal? = null
    var horizontalSignal: HorizontalSignal? = null

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Municipalities> = object : Parcelable.Creator<Municipalities> {
            override fun newArray(size: Int): Array<Municipalities?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): Municipalities = Municipalities(source)
        }

    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeString(typeSignal)
        }
    }

    override fun describeContents(): Int = 0

    override fun toString(): String {
        var typeSignalMain = ""
        if (verticalSignal != null) {
            typeSignalMain = "$verticalSignal"
        } else if (horizontalSignal != null) {
            typeSignalMain = "$horizontalSignal"
        }
        return "\"$count\":{\"typeSignal\":\"$typeSignal\",$imagesByCode,$locationSignal,$cittusSignal,$typeSignalMain}"
    }


}