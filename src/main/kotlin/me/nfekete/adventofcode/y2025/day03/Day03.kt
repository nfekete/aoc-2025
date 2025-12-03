package me.nfekete.adventofcode.y2025.day03

import me.nfekete.adventofcode.y2024.common.classpathFile

private fun String.maxJoltage() =
    map { it.digitToInt() }.let { digits ->
        digits.dropLast(1).max().let { msd ->
            msd * 10 + digits.dropWhile { it < msd }.drop(1).max()
        }
    }

private fun main() {
    val input = classpathFile("day03/input.txt")
        .readLines()

    input.sumOf { it.maxJoltage() }.also { println("Part1: $it") }
}
