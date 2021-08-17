package com.steve.craftsmanship.model

/**
 * This class is for Driver. When we have driver's name, we can also calculate the vowels and consonants.
 * the suitabilityScore will be used later.
 */
data class Driver(val name: String) {
    val vowels: Int
    val consonants: Int
    private val vowelFilter = "aeiou"

    init {
        consonants = calculateNumberOfConsonants(name)
        vowels = calculateNumberOfVowels(name)
    }

    private fun getLowercaseNameWithoutSpace(name: String) : String {
        return name.replace(" ", "").lowercase()
    }

    private fun calculateNumberOfVowels(name: String): Int {
        val nameWithoutSpace = getLowercaseNameWithoutSpace(name)
        // return nameWithoutSpace.length - consonants // It is a faster way, but this method depend on other fun's result.
        val nameVowelsOnly = nameWithoutSpace.filter { vowelFilter.indexOf(it) < 0 }  // consonants will be filtered out.
        return nameVowelsOnly.length
    }

    private fun calculateNumberOfConsonants(name: String): Int {
        val nameWithoutSpace = getLowercaseNameWithoutSpace(name)
        // remove all vowels
        val nameWithoutVowels = nameWithoutSpace.filter { vowelFilter.indexOf(it) >= 0 } // Vowels will be filtered out
        return nameWithoutVowels.length
    }
}