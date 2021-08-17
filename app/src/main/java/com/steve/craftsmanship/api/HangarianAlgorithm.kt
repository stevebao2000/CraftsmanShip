package com.steve.craftsmanship.api

import com.steve.craftsmanship.model.MyResult
import com.steve.craftsmanship.model.PairInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * This Hangarian Algorithm is taken from stackOverflow with small changes.
 * The original method only return the max sum. We actually need the mapping from drivers
 * to shipments. I added a map (resultMap) to record the track (List of pairs). When
 * we have the max sum (key), we can easily get the list of pairs.
 */
class HangarianAlgorithm {

    var resultMap: MutableMap<Int, List<PairInt>> = mutableMapOf()

    // Hungarian Algorithm: modified from the algorithm provided by Chetan Kumar on StackOverflow.
    private suspend fun getMaxSum(
        row: Int, col: IntArray, maxtrixSize: Int, mat: Array<IntArray>, sum: Int,
        result: MyResult
    ): MyResult {
//        val pairs = mutableListOf<PairInt>().apply {addAll(result.pairs)}
        val pairs = result.pairs.map {it.copy()}.toMutableList()
        if (row >= maxtrixSize) {
            resultMap.put(sum, mutableListOf<PairInt>().apply {addAll(result.pairs)})
            return MyResult(result.pairs, sum)
        }
        var max = Int.MIN_VALUE

        for (i in 0 until maxtrixSize) {
            if (col[i] == 1) continue
            col[i] = 1
            result.pairs.add(PairInt(row, i))
            val nextResult = getMaxSum(row + 1, col, maxtrixSize, mat, sum + mat[row][i], result)
            max = Math.max(nextResult.sum, max)
            result.pairs.removeLast()
            col[i] = 0
        }

        // even I made a copy from result.pairs to pairs, during the for loop above, when result.pairs.removeLast()
        // called, the elements in pairs also removed.
        return MyResult(pairs, max)
    }

    suspend fun findMatchesForMaxSuitabilityScores(matrix: Array<IntArray>, size: Int) : MyResult{
        val n : Int = size
        val col = IntArray(size) {0}
        val ans = MyResult()
        val result = getMaxSum(0, col, n, matrix, 0, ans)
        val pairs = resultMap.get(result.sum)
        if (pairs == null)
            throw Exception("Error: No best result found for drivers and shipments")

        return MyResult(pairs.toMutableList(), result.sum)
    }
}