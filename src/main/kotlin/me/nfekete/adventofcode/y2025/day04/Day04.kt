package me.nfekete.adventofcode.y2025.day04

import me.nfekete.adventofcode.y2025.common.Grid2D
import me.nfekete.adventofcode.y2025.common.classpathFile

private fun main() {
    val grid = classpathFile("day04/input.txt")
        .readLines()
        .let { lines -> Grid2D.from(lines) { it == '@' } }

    grid.coords.count { coord -> coord.neighbors.count { grid.map[it] != null } < 4 }
        .also { println("Part1: $it") }

    generateSequence(grid to 0) { (grid, removed) ->
        val removables = grid.coords.filter { coord -> coord.neighbors.count { grid.map[it] != null } < 4 }.toSet()
        Grid2D(grid.map - removables) to (removed + removables.size)
    }.windowed(2).takeWhile { (a, b) -> a != b }.last().last().second
        .also { println("Part2: $it") }

}
