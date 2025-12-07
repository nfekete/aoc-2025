package me.nfekete.adventofcode.y2025.day07

import me.nfekete.adventofcode.y2025.common.Grid2D
import me.nfekete.adventofcode.y2025.common.Grid2D.Coord
import me.nfekete.adventofcode.y2025.common.Grid2D.Direction
import me.nfekete.adventofcode.y2025.common.classpathFile

private fun part1(grid: Grid2D<Char>): Long {
    val start = grid.map.entries.single { (k, v) -> v == 'S' }.key

    fun recurse(coord: Coord, visited: MutableSet<Coord>): Long {
        if (coord in visited) return 0
        visited.add(coord)
        return when {
            coord !in grid.coords -> 0
            grid[coord] == '^' -> 1 + recurse(coord + Direction.LEFT.delta, visited) +
                    recurse(coord + Direction.RIGHT.delta, visited)

            else -> recurse(coord + Direction.DOWN.delta, visited)
        }
    }

    return recurse(start, mutableSetOf())
}

private fun part2(grid: Grid2D<Char>): Long {
    val start = grid.map.entries.single { (k, v) -> v == 'S' }.key

    fun recurse(coord: Coord, visited: Set<Coord>): Long {
        if (coord in visited) return 0
        visited.add(coord)
        return when {
            coord !in grid.coords -> 0
            grid[coord] == '^' -> 1 + recurse(coord + Direction.LEFT.delta, visited) +
                    recurse(coord + Direction.RIGHT.delta, visited)

            else -> recurse(coord + Direction.DOWN.delta, visited)
        }
    }

    return recurse(start, mutableSetOf())
}

private fun main() {
    val grid = classpathFile("day07/sample.txt")
        .readLines()
        .let { Grid2D.from(it) { true } }

    part1(grid).also { println("Part1: $it") }
    part2(grid).also { println("Part2: $it") }
}
