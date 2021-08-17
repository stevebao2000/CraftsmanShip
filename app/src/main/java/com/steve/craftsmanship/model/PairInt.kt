package com.steve.craftsmanship.model

data class PairInt(val driverIndex:Int, val addressIndex: Int) {
    override fun toString() : String{
        return "[$driverIndex, $addressIndex]"
    }
}