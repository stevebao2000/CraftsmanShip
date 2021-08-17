package com.steve.craftsmanship.model

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.steve.craftsmanship.util.Util
import java.lang.Exception

class MainViewModel : ViewModel() {
    var driverNames: MutableList<String> = mutableListOf()
    var shipments: MutableList<String> = mutableListOf()
    lateinit var driverShipmentScoreMatrix: Array<IntArray> // matrix for suitability scores.
    var driverToShipIndexList: MutableList<Int> = mutableListOf() // drivers to shipments index map
    var mSize : Int = 0  // matrix size
    var maxSum: Int = 0

    fun updateShipmentDriverSuitabilityScoreMatrix() {
        mSize = driverNames.size
        // We know the mSize now, so we can initialize the matrix.
        driverShipmentScoreMatrix = Array(mSize) {IntArray(mSize){0} }
        // For each driver, there is an array of scores for each shipment.
        for (i: Int in 0 until mSize) {
            driverShipmentScoreMatrix[i] = getArrayOfShipmentsForDriver(driverNames.get(i))
        }
    }

    fun isMatrixInitialized() : Boolean {
        return this::driverShipmentScoreMatrix.isInitialized
    }

    fun getArrayOfShipmentsForDriver(driverName: String) : IntArray{
        val array = IntArray(mSize) {0}

        for (j: Int in 0 until mSize)
            array[j] = calculateSuitabilityScore(driverName, shipments.get(j))

        return array
    }

    fun getStreetNameLength(address: String) : Int {
        val addressArray = address.split(" ").toMutableList()

        // remove street number first
        if (addressArray[0].isDigitsOnly()) {
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

    fun getShipmentAddress(i: Int) : String {
        if (i >= 0 && i < mSize)
            return shipments.get(i)
        else
            throw Exception("Error: index out of bound!")
    }
}