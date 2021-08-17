package com.steve.craftsmanship

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.steve.craftsmanship.adapter.MyAdapter
import com.steve.craftsmanship.adapter.OnItemClickListener
import com.steve.craftsmanship.api.HangarianAlgorithm
import com.steve.craftsmanship.databinding.ActivityMainBinding
import com.steve.craftsmanship.model.MainViewModel
import com.steve.craftsmanship.model.MyResult
import com.steve.craftsmanship.model.ShipmentsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private val TAG = "MainActivity"
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var processed = false
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        /**
         * In case of rotation screen, we do not need to process the data again. In the real product, we might think to
         * save the matrix to object or hard disk, as it takes such long time to calculate.
         */
        if (mainViewModel.isMatrixInitialized())
            processed = true

        if (!processed)
            convertJsonFileToClass()
        recyclerView = binding.list
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(mainViewModel.driverNames, this)
        recyclerView.adapter = adapter

        if (!processed)
            startHungarianAlgorithmCalculation()
        else
            resetVisibility()
    }

    // It takes long time to calculate, so we put this on heavy calculation thread.
    // With Coroutine, it is simple.
    private fun startHungarianAlgorithmCalculation() {
        CoroutineScope(Default).launch {
            findMaxMatches()
        }
    }

    private suspend fun findMaxMatches() {
        val result = HangarianAlgorithm().findMatchesForMaxSuitabilityScores(mainViewModel.driverShipmentScoreMatrix, mainViewModel.driverNames.size)
        showResult(result)
    }

    /**
     * After we received the result, we switch back to main thread to display the result on the screen.
     */
    private suspend fun showResult(result: MyResult) {
        withContext(Main) {
            // print the max sum and into to logcat as reference.
            println("Sum = ${result.sum}, driver to address index: ${result.pairs}")
            mainViewModel.maxSum = result.sum
            result.pairs.sortBy { it.driverIndex }
            mainViewModel.driverToShipIndexList.addAll(result.pairs.map {it.addressIndex})
            print("After sort, shipment address index: ")
            println(mainViewModel.driverToShipIndexList)
            resetVisibility()
        }
    }

    private fun convertJsonFileToClass() {
        val srcFileId: Int = R.raw.data
        val jsonFileReader  =
            BufferedReader(InputStreamReader(applicationContext.getResources().openRawResource(srcFileId)))

        val shipmentData = Gson().fromJson(jsonFileReader, ShipmentsData::class.java)
        if (shipmentData.Drivers.size != shipmentData.shipments.size)
            throw Exception("Bad json file: number of drivers is NOT equal to number of shipments")
        updateShipmentdataOnMainViewModel(shipmentData)
    }

    private fun updateShipmentdataOnMainViewModel(shipmentData: ShipmentsData) {
        mainViewModel.driverNames.addAll(shipmentData.Drivers)
        mainViewModel.shipments.addAll(shipmentData.shipments)
        mainViewModel.updateShipmentDriverSuitabilityScoreMatrix()
    }

    private fun resetVisibility() {
        binding.address.text = getString(R.string.click_ship_addr)
        binding.progressBar.visibility = View.GONE
        binding.list.visibility = View.VISIBLE
        binding.nameLable.visibility = View.VISIBLE
        binding.driverName.visibility = View.VISIBLE
        binding.addressLable.visibility = View.VISIBLE
    }

    override fun onItemClicked(view: TextView, index: Int) {
        val addressIndex = mainViewModel.driverToShipIndexList.get(index)
        val address = mainViewModel.getShipmentAddress(addressIndex)
        binding.address.text = address
        binding.driverName.text = view.text
    }
}