package com.steve.craftsmanship.model

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.steve.craftsmanship.util.Util
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var initRule: MockitoRule = MockitoJUnit.rule()

    var driverNames: MutableList<String> = mutableListOf(
        "Cleve Durgan",
        "Murphy Mosciski",
        "Kaiser Sose")
    var shipments: MutableList<String> = mutableListOf(
        "63187 Volkman Garden suite 447",
        "75855 Dessie Lights",
        "1797 Adolf island Apt. 744")
    val util = Util()
    val mainViewModel = MainViewModel()

    @Before
    fun setUp() {
        mainViewModel.shipments.addAll(shipments)
        mainViewModel.driverNames.addAll(driverNames)
        mainViewModel.mSize = driverNames.size
    }

    @Test
    fun testGetSuitabilityScore() {
        val name = driverNames.get(1)
        val address = shipments.get(0)
        val result = util.calculateSuitabilityScore(name, address)
        assertEquals(15, result)
    }

    @Test
    fun testGetShipmentAddress(){
        assertEquals(shipments.get(1), mainViewModel.getShipmentAddress(1) )
    }

    @Test
    fun testGetArraySizeOfShipmentsForDriver() {
        val result = mainViewModel.getArrayOfShipmentsForDriver(driverNames.get(1))

        assertEquals(result.size, mainViewModel.mSize)
    }
}