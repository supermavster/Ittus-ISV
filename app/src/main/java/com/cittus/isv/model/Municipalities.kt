package com.cittus.isv.model

import android.os.Parcel
import android.os.Parcelable

data class Municipalities(
    var idInventario: Int?,
    var idListSignal: Int?,
    var idMaxSignal: Int?,
    var nameMunicipal: String?,
    var nameDepartment: String?
) : Parcelable {

    // 0 -> Id Inventario
    // 1 -> Id Lista Senal
    // 2 -> Id Max Signal
    // 3 -> Municipio
    // 4 -> Departamento


    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Municipalities> = object : Parcelable.Creator<Municipalities> {
            override fun newArray(size: Int): Array<Municipalities?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): Municipalities = Municipalities(source)
        }

    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            idInventario?.let { it1 -> dest.writeInt(it1) }
            idListSignal?.let { it1 -> dest.writeInt(it1) }
            idMaxSignal?.let { it1 -> dest.writeInt(it1) }
            dest.writeString(nameMunicipal)
            dest.writeString(nameDepartment)
        }
    }

    override fun describeContents(): Int = 0
    override fun toString(): String {
        return "Municipalities(idInventario=$idInventario, idListSignal=$idListSignal, idMaxSignal=$idMaxSignal, nameMunicipal='$nameMunicipal', nameDepartment='$nameDepartment')"
    }


}


