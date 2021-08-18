package com.steve.craftsmanship.model

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val driverNameLiveData = MutableLiveData<String>()
    val driverName: LiveData<String> = driverNameLiveData

    fun updateDriverName(name: String) {
        driverNameLiveData.postValue(name)
    }

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
            array[j] = Util().calculateSuitabilityScore(driverName, shipments.get(j))

        return array
    }

    fun getShipmentAddress(i: Int) : String {
        if (i >= 0 && i < mSize)
            return shipments.get(i)
        else
            throw Exception("Error: index out of bound!")
    }
}