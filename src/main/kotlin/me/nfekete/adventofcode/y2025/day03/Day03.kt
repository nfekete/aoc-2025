package me.nfekete.adventofcode.y2025.day03

import me.nfekete.adventofcode.y2024.common.classpathFile

private fun String.maxJoltage(joltageDigits: Int) =
    map { it.digitToInt() }.let { allDigits ->
        (joltageDigits-1 downTo 0).fold(allDigits to emptyList<Int>()) { (digits, outputDigits), exponent ->
            digits.dropLast(exponent).withIndex().maxBy { it.value }.let {
                digits.drop(it.index + 1) to outputDigits + it.value
            }
        }.second.joinToString(separator = "").toLong()
    }

private fun main() {
    val input = classpathFile("day03/input.txt")
        .readLines()

    input.sumOf { it.maxJoltage( 2) }.also { println("Part1: $it") }
    input.sumOf { it.maxJoltage(12) }.also { println("Part2: $it") }
}
