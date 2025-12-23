package me.nfekete.adventofcode.y2025.day09

import me.nfekete.adventofcode.y2025.common.Grid2D.Coord
import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.crossProduct
import me.nfekete.adventofcode.y2025.common.intersects
import me.nfekete.adventofcode.y2025.common.range
import me.nfekete.adventofcode.y2025.common.removeBoundaries

private data class Line(val a: Coord, val b: Coord) {
    val xRange = (a.x to b.x).range
    val yRange = (a.y to b.y).range
}

private fun main() {
    val input = classpathFile("day09/input.txt")
        .readLines()
        .map { line -> line.split(",").map { it.toLong() }.let { (x, y) -> Coord(x, y) } }

    val rectangles = (input crossProduct input)
        .filter { (a, b) -> a < b }
        .map { (a, b) -> Triple(a, b, ((a - b).absoluteValue + Coord.one).area) }
    val edges = (input + input.first()).windowed(2).map { (a, b) -> Line(a, b) }

    rectangles.maxOf { it.third }.also { println("Part1: $it") }

    rectangles
        .sortedByDescending { it.third }
        .first { (a, b) ->
            val xRange = (a.x to b.x).range.removeBoundaries()
            val yRange = (a.y to b.y).range.removeBoundaries()
            edges.none { it.xRange intersects xRange && it.yRange intersects yRange }
        }.also { println("Part2: ${it.third}") }
}
