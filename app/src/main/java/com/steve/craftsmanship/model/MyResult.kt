package com.steve.craftsmanship.model

/**
 * This is the result returned by Hangarian Algorithm. We need the list of driver index and address index pair for future use.
 * the last return result should be the max sum.
 */
class MyResult (
    var pairs: MutableList<PairInt> = mutableListOf(),
    var sum: Int = 0
)