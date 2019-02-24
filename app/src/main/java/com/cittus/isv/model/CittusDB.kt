package com.cittus.isv.model

class CittusInventario{
    var IdInventario = 0;
    var IdMunicipio = "";

    constructor(){}

    constructor(IdInventario: Int, IdMunicipio: String) {
        this.IdInventario = IdInventario
        this.IdMunicipio = IdMunicipio
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

    // Signal
    var IdSIgnal = 0
    var Latitud:Float = 0.0f
    var Longitud:Float = 0.0f
    var TypeSignal = ""
    var Direccion = ""
    var Location = ""
    var Codigo:String = ""
    var Lado:String = ""
    var Estado:String = ""
    var TipoFijacion:String = ""
    var EstadoFijacion:String = ""
    var Simbolo:String = ""

    constructor()

    constructor(
        IdSIgnal: Int,
        Latitud: Float,
        Longitud: Float,
        Codigo: String,
        Lado: String,
        Estado: String,
        TipoFijacion: String,
        EstadoFijacion: String,
        Simbolo: String
    ) {
        this.IdSIgnal = IdSIgnal
        this.Latitud = Latitud
        this.Longitud = Longitud
        this.Codigo = Codigo
        this.Lado = Lado
        this.Estado = Estado
        this.TipoFijacion = TipoFijacion
        this.EstadoFijacion = EstadoFijacion
        this.Simbolo = Simbolo
    }

    override fun toString(): String {
        return "CittusSignal(IdSIgnal=$IdSIgnal, Latitud=$Latitud, Longitud=$Longitud, Codigo='$Codigo', Lado='$Lado', Estado='$Estado', TipoFijacion='$TipoFijacion', EstadoFijacion='$EstadoFijacion', Simbolo='$Simbolo')"
    }


}

