package com.cittus.isv.model

import android.net.Uri

class CittusInventario{
    var IdInventario = 0;
    var IdMunicipio = "";

    constructor(){}

    constructor(IdInventario: Int, IdMunicipio: String) {
        this.IdInventario = IdInventario
        this.IdMunicipio = IdMunicipio
    }

    fun getInventarioID(): Int {
        return this.IdInventario
    }

    fun getMunicipioID(): String {
        return this.IdMunicipio
    }

    override fun toString(): String {
        return "{\"IdInventario\":$IdInventario, \"IdMunicipio\":\"$IdMunicipio\"}"
    }

}

class CittusListSignal{
    var IdInventario = 0;
    var IdSignal = 0;

    var signal: ArrayList<CittusSignal>? = null
    var inventarioMain: CittusInventario? = null

    constructor()

    constructor(IdInventario: Int, IdSignal: Int) {
        this.IdInventario = IdInventario
        this.IdSignal = IdSignal
    }

    fun setSignalMain(signal: ArrayList<CittusSignal>){
        this.signal = signal
    }

    fun setInventario(inventarioMain: CittusInventario){
        this.inventarioMain = inventarioMain
    }

    override fun toString(): String {
        return "CittusListSignal(IdInventario=$IdInventario, IdSignal=$IdSignal, signal=$signal, inventarioMain=$inventarioMain)"
    }


}

class CittusSignal(idSignal: Int) {

    var Latitud:Float = 0.0f
    var Longitud:Float = 0.0f

    // Fotos
    var PhotoFront: Uri? = null
    var PhotoBack:String = ""
    var PhotoPlaque:String = ""

    // Images
    var Simbolo:String = ""
    var Codigo:String = ""

    // Horizontal
    var TypeSignal = ""
    var Direccion = ""
    var Location = ""
    var Carril = ""
    var Porcentaje = ""

    // Information
    var LocationMain = ""
    var Size = ""
    var TipoFijacion:String = ""
    var EstadoFijacion:String = ""

    // General Data
    var LocationBetween_1 = ""
    var LocationBetween_2 = ""
    var LocationBetween_3 = ""
    var Estado:String = ""


    init {
        IdSignal = idSignal;
    }

    companion object {
        // Signal
        var IdSignal = 0
    }

    override fun toString(): String {
        return "{\"IdSIgnal\":\"$IdSignal\",\"Latitud\":\"$Latitud\", \"Longitud\":\"$Longitud\", \"PhotoFront\":\"$PhotoFront\", \"PhotoBack\":\"$PhotoBack\", \"PhotoPlaque\":\"$PhotoPlaque\", \"Simbolo\":\"$Simbolo\", \"Codigo\":\"$Codigo\", \"TypeSignal\":\"$TypeSignal\", \"Direccion\":\"$Direccion\", \"Location\":\"$Location\", \"Carril\":\"$Carril\", \"Porcentaje\":\"$Porcentaje\", \"LocationMain\":\"$LocationMain\", \"Size\":\"$Size\", \"TipoFijacion\":\"$TipoFijacion\", \"EstadoFijacion\":\"$EstadoFijacion\", \"LocationBetween_1\":\"$LocationBetween_1\", \"LocationBetween_2\":\"$LocationBetween_2\", \"LocationBetween_3\":\"$LocationBetween_3\", \"Estado\":\"$Estado\"}"
    }


}

