package me.nfekete.adventofcode.y2025.day09

import me.nfekete.adventofcode.y2025.common.Grid2D
import me.nfekete.adventofcode.y2025.common.Grid2D.Coord
import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.crossProduct

private fun main() {
    val input = classpathFile("day09/input.txt")
        .readLines()
        .map { line -> line.split(",").map { it.toLong() }.let { (x, y) -> Grid2D.Coord(x, y) } }

    (input crossProduct input)
        .filter { (a, b) -> a < b }
        .maxOf { (a, b) -> ((a - b).absoluteValue + Coord.one).area }
        .also { println("Part1: $it") }
}
