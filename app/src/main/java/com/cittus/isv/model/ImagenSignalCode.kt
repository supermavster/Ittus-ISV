package com.cittus.isv.model

class ImagenSignalCode {

    var idImagen = 0
    var codeImagen = ""
    var pathImagen = ""


    override fun toString(): String {
        return "\"ImagenSignalCode\":{\"idImagen\":$idImagen, \"codeImagen\":\"$codeImagen\", \"pathImagen\":\"$pathImagen\"}"
    }


}
