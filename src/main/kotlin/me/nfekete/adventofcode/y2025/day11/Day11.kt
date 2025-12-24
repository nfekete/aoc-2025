package me.nfekete.adventofcode.y2025.day11

import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.map2
import me.nfekete.adventofcode.y2025.common.memoized
import me.nfekete.adventofcode.y2025.common.splitByDelimiter

private class Problem(val map: Map<String, List<String>>) {
    fun recurse(from: String, to: String): Long =
        if (from == to)
            1
        else
            map[from]?.sumOf { recurseM(it, to) } ?: 0

    val recurseM = ::recurse.memoized()
    fun part1() = recurseM("you", "out")
    fun part2(): Long {
        val fftFirst = recurseM("fft", "dac") > 0
        val (c1, c2) = if (fftFirst) "fft" to "dac" else "dac" to "fft"
        val leg1 = recurseM("svr", c1)
        val leg2 = recurseM(c1, c2)
        val leg3 = recurseM(c2, "out")
        return leg1 * leg2 * leg3
    }

}

private fun main() {
    val input = classpathFile("day11/input.txt")
        .readLines()
        .associate { line ->
            line.splitByDelimiter(": ").map2 { it.split(' ') }
        }

    Problem(input).let { problem ->
        problem.part1().also { println("Part1: $it") }
        problem.part2().also { println("Part2: $it") }
    }
}
