package com.cittus.isv.model

class Municipalities {

    var IdInventario = 0;
    var IdMunicipio = "";

    constructor() {}

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
