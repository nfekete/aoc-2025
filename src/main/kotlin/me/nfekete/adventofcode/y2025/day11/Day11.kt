package me.nfekete.adventofcode.y2025.day11

import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.map2
import me.nfekete.adventofcode.y2025.common.splitByDelimiter

private fun Map<String, List<String>>.part1(): Long {
    fun recurse(from: String, to: String): Long =
        if (from == to)
            1
        else
            getValue(from).sumOf { recurse(it, to) }

    return recurse("you", "out")
}

private fun main() {
    val input = classpathFile("day11/input.txt")
        .readLines()
        .associate { line ->
            line.splitByDelimiter(": ").map2 { it.split(' ') }
        }

    input.part1().also { println("Part1: $it") }
}
