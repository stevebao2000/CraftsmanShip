package com.steve.craftsmanship.util

import com.steve.craftsmanship.model.Driver

class Util {

    // test if two numbers has common factor (>1)
    fun hasCommonFactors(n1: Int, n2: Int) : Boolean {
        var i = 1
        var gcd = 1
        while (i <= n1 && i <= n2) {
            // Checks if i is factor of both integers
            if (n1 % i == 0 && n2 % i == 0) {
                gcd = i
                if (gcd > 1)
                    break
            }
            ++i
        }
        return gcd > 1
    }

    fun isEvenNumber(num: Int) : Boolean {
        return (num % 2 == 0)
    }

    fun digitalsOnly(s: String) : Boolean {
        s.toCharArray().map { if( '0'> it || it > '9') return false}
        return true
    }
    fun getStreetNameLength(address: String) : Int {
        val addressArray = address.split(" ").toMutableList()

        // remove street number first
        if (digitalsOnly(addressArray[0])) {
            addressArray.removeFirst() // remove street number
        }
        // remove Suite number or apartment number if this is the case.
        // the apt number or suite number could be "A", "B"..., not necessary digit numbers
        val nextTolast = addressArray.get(addressArray.size-2)
        if (nextTolast.compareTo("Apt.", true) == 0 || nextTolast.compareTo("Suite", true) == 0) {
            addressArray.removeLast() // remove apartment or suite number
            addressArray.removeLast() // remove "Apt." or "Suite"
        }
        return addressArray.joinToString(" ").length
    }

    // calculate suitability score based the instruction
    fun calculateSuitabilityScore(name: String, shipAddress: String) : Int {
        val driver = Driver(name)
        val nameLen = name.length
        val addressLen = getStreetNameLength(shipAddress)
        var suitabilityScore = 0
        if (Util().isEvenNumber(addressLen)) {
            suitabilityScore = (driver.vowels * 1.5).toInt()
        } else
            suitabilityScore = driver.consonants

        if (Util().hasCommonFactors(nameLen, addressLen))
            suitabilityScore = (suitabilityScore * 1.5).toInt()

        return suitabilityScore
    }

}