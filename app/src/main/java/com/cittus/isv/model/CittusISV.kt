package com.cittus.isv.model

class CittusISV {

    var typeSignal: String? = null // Check -> Enum
    var imagesByCode: ImagenSignalCode? = null
    var locationSignal: LocationSignal? = null
    var cittusSignal: CittusSignal? = null
    var verticalSignal: VerticalSignal? = null
    var horizontalSignal: HorizontalSignal? = null


    override fun toString(): String {
        return "CittusISV(typeSignal=$typeSignal, imagesByCode=$imagesByCode, locationSignal=$locationSignal, cittusSignal=$cittusSignal, verticalSignal=$verticalSignal, horizontalSignal=$horizontalSignal)"
    }


}