package com.steve.craftsmanship.util

import org.junit.Test
import org.junit.Assert.*

class UtilTest {
    val util = Util()

    @Test
    fun testHasCommonFactors(){
        val a = 3
        val b = 7
        val c = 14

        assertFalse(util.hasCommonFactors(a,b))
        assertTrue(util.hasCommonFactors(b, c))
    }

    @Test
    fun testIsEvenNumber(){
        assertTrue(util.isEvenNumber(72))
        assertFalse(util.isEvenNumber(53))
    }

    @Test
    fun testGetStreetNameLength() {
        val street1 = "8725 Aufderhar River Suite 859"
        val street2 = "2431 Lindgren Corners"

        assertEquals(util.getStreetNameLength(street1), 15)
        assertEquals(util.getStreetNameLength(street2), 16)
    }

    @Test
    fun calculateSuitabilityScore() {
        val street1 = "8725 Aufderhar River Suite 859"
        val street2 = "2431 Lindgren Corners"
        val name1 = "Cleve Durgan"
        val name2 = "Murphy Mosciski"

        assertEquals(6, util.calculateSuitabilityScore(name1, street1))
        assertEquals(15, util.calculateSuitabilityScore(name2, street2))
    }
}