package co.edu.uptc.aj.os

import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Random

fun Random.intBetween(min: Int, max: Int): Int = nextInt(max + 1 - min) + min

fun ignored(what: () -> Unit) {
    try {
        what()
    } catch (e: Exception) {
        // Ignored exception
    }
}