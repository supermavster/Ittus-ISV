package com.cittus.isv.model

// Main Class
class CittusListSignal {

    // Check Login
    var login: Boolean? = null
    // Inventario Main
    var municipality: Municipalities? = null

    // Signals
    var signal: ArrayList<CittusISV>? = null
    // Add ID by Signal
    var geolocationCardinalImages: ArrayList<GeolocationCardinalImages>? = null


    constructor()

    /*constructor(IdInventario: Int, IdSignal: Int) {
        this.IdInventario = IdInventario
        this.IdSignal = IdSignal
    }*/

    fun setSignalMain(signal: ArrayList<CittusISV>) {
        this.signal = signal
    }

    fun setInventario(inventarioMain: Municipalities) {
        this.municipality = inventarioMain
    }

    override fun toString(): String {
        return "CittusListSignal(signal=$signal, inventarioMain=$municipality)"
        //IdInventario=$IdInventario, IdSignal=$IdSignal,
    }
}