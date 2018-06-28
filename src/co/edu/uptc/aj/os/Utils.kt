package co.edu.uptc.aj.os

import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Random

fun Random.intBetween(min: Int, max: Int): Int = nextInt(max + 1 - min) + min

fun Number.round(decimalCount: Int): String {
    val expression = StringBuilder().append("#.")
    (1..decimalCount).forEach { expression.append("#") }
    val formatter = DecimalFormat(expression.toString())
    formatter.roundingMode = RoundingMode.HALF_UP
    return formatter.format(this)
}

fun ignored(what: () -> Unit) {
    try {
        what()
    } catch (e: Exception) {
        // Ignored exception
    }
}