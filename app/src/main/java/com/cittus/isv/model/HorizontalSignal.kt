package com.cittus.isv.model

class HorizontalSignal {

    var locationOnTheWay: String = ""
    var directionJourney: String = ""
    var percentage: String = ""
    var rail: String = ""

    override fun toString(): String {
        return "\"HorizontalSignal\":{\"locationOnTheWay\":\"$locationOnTheWay\", \"directionJourney\":\"$directionJourney\", \"percentage\":\"$percentage\", \"rail\":\"$rail\"}"
    }

}
