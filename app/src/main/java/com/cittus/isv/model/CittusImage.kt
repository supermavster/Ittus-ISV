package com.cittus.isv.model

import android.os.Parcel
import android.os.Parcelable

data class CittusImage(
    var title: String,
    var url_img: String,
    var code: Int,
    var Action: Int
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CittusImage> = object : Parcelable.Creator<CittusImage> {
            override fun newArray(size: Int): Array<CittusImage?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): CittusImage = CittusImage(source)
        }

    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeString(title)
            dest.writeString(url_img)
            dest.writeInt(code)
            dest.writeInt(Action)
        }
    }

    override fun describeContents(): Int = 0


    override fun toString(): String {
        return "CittusImage(title='$title', url_img='$url_img', code=$code, Action=$Action)"
    }


}