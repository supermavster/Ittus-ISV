package com.cittus.isv.model

import java.util.*

class LocationSignal {

    var firstAddressSignal: Array<String>? = null
    var secondAddressSignal: Array<String>? = null
    var thirdAddressSignal: Array<String>? = null

    override fun toString(): String {
        return "LocationSignal(firstAddressSignal=${Arrays.toString(firstAddressSignal)}, secondAddressSignal=${Arrays.toString(
            secondAddressSignal
        )}, thirdAddressSignal=${Arrays.toString(thirdAddressSignal)})"
    }


}
