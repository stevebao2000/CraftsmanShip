package com.steve.craftsmanship.util

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
}