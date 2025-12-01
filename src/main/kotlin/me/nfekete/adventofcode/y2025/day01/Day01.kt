package me.nfekete.adventofcode.y2025.day01

import me.nfekete.adventofcode.y2024.common.classpathFile
import kotlin.math.absoluteValue
import kotlin.math.sign

private fun String.parseLine() = if (startsWith("L")) -drop(1).toInt() else drop(1).toInt()

private fun main() {
    val input = classpathFile("day01/input.txt")
        .readLines()
        .map { it.parseLine() }

    input.scan(50) { acc, i -> (acc + i) % 100 }
        .count { it == 0 }
        .also { println("Part1: $it") }

    input.flatMap { i -> List(i.absoluteValue) { i.sign } }
        .scan(50) { acc, i -> (acc + i) % 100 }
        .count { it == 0 }
        .also { println("Part2: $it") }
}
