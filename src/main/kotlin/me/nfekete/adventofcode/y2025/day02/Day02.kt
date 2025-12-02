package me.nfekete.adventofcode.y2025.day02

import me.nfekete.adventofcode.y2024.common.classpathFile

private fun Long.isSillyNumberP1(): Boolean =
    toString().let { it.length % 2 == 0 && it.take(it.length / 2) == it.drop(it.length / 2) }

private fun Long.isSillyNumberP2(): Boolean =
    toString().let { str ->
        (1..str.length / 2)
            .filter { str.length % it == 0 }
            .any { str.chunked(it).toSet().count() == 1 }
    }

private fun main() {
    val input = classpathFile("day02/input.txt")
        .readLine()
        .split(",")
        .map { it.split("-").map(String::toLong).let { (a, b) -> a..b } }

    input.sumOf { range -> range.filter { it.isSillyNumberP1() }.sum() }.also { println("Part1: $it") }
    input.sumOf { range -> range.filter { it.isSillyNumberP2() }.sum() }.also { println("Part2: $it") }
}
