package com.cittus.isv.model

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
        return "CittusInventario(IdInventario=$IdInventario, IdMunicipio='$IdMunicipio')"
    }

}

class CittusListSignal{
    var IdInventario = 0;
    var IdSignal = 0;

    constructor()

    constructor(IdInventario: Int, IdSignal: Int) {
        this.IdInventario = IdInventario
        this.IdSignal = IdSignal
    }

    override fun toString(): String {
        return "CittusListSignal(IdInventario=$IdInventario, IdSignal=$IdSignal)"
    }


}

class CittusSignal {

    var Latitud:Float = 0.0f
    var Longitud:Float = 0.0f

    // Fotos
    var PhotoFront:String = ""
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



    constructor(){
        ++IdSignal
    }

    companion object {
        // Signal
        var IdSignal = 0
    }

    override fun toString(): String {
        return "CittusSignal(IdSIgnal=$IdSignal, Latitud=$Latitud, Longitud=$Longitud, PhotoFront='$PhotoFront', PhotoBack='$PhotoBack', PhotoPlaque='$PhotoPlaque', Simbolo='$Simbolo', Codigo='$Codigo', TypeSignal='$TypeSignal', Direccion='$Direccion', Location='$Location', Carril='$Carril', Porcentaje='$Porcentaje', LocationMain='$LocationMain', Size='$Size', TipoFijacion='$TipoFijacion', EstadoFijacion='$EstadoFijacion', LocationBetween_1='$LocationBetween_1', LocationBetween_2='$LocationBetween_2', LocationBetween_3='$LocationBetween_3', Estado='$Estado')"
    }


}

