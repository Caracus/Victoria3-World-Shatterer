package org.victoriaThreeShatterer.models

import com.fasterxml.jackson.annotation.JsonProperty

data class RgbColor(
    @JsonProperty("R")
    val red: String,
    @JsonProperty("G")
    val green: String,
    @JsonProperty("B")
    val blue: String
) {
    override fun toString(): String {
        return "$red $green $blue"
    }

    //randomizes all rgb values within variety range
    fun toRange(range: Int): String {
        var rangedRed = toRgbLimit((-range..range).random() + red.toInt())
        var rangedGreen = toRgbLimit((-range..range).random() + green.toInt())
        var rangedBlue = toRgbLimit((-range..range).random() + blue.toInt())

        return "$rangedRed $rangedGreen $rangedBlue"
    }

    //changes only a single r g or b value
    fun toRangeSingle(range: Int): String {
        return when ((1..3).random()) {
            1 -> "${toRgbLimit((-range..range).random() + red.toInt())} $green $blue"
            2 -> "$red ${toRgbLimit((-range..range).random() + green.toInt())} $blue"
            3 -> "$red $green ${toRgbLimit((-range..range).random() + blue.toInt())}"
            else -> throw RuntimeException("Random number not matching")
        }
    }

    //ensures that rgb values remain valid
    private fun toRgbLimit(value: Int): Int {
        return when (value) {
            in 0..255 -> value
            in 256..Int.MAX_VALUE -> 255
            else -> 0
        }
    }
}