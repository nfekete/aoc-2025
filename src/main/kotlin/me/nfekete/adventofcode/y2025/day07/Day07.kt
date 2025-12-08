package me.nfekete.adventofcode.y2025.day07

import me.nfekete.adventofcode.y2025.common.Grid2D
import me.nfekete.adventofcode.y2025.common.Grid2D.Coord
import me.nfekete.adventofcode.y2025.common.Grid2D.Direction
import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.memoized

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

class Part2(private val grid: Grid2D<Char>) {

    val recurseM = ::recurse.memoized()
    fun recurse(coord: Coord): Long {
        return when {
            coord !in grid.coords -> 1
            grid[coord] == '^' -> recurseM(coord + Direction.LEFT.delta) +
                    recurseM(coord + Direction.RIGHT.delta)

            else -> recurseM(coord + Direction.DOWN.delta)
        }
    }


    fun part2(): Long {
        val start = grid.map.entries.single { (k, v) -> v == 'S' }.key
        return recurseM(start)
    }
}

private fun main() {
    val grid = classpathFile("day07/input.txt")
        .readLines()
        .let { Grid2D.from(it) { true } }

    part1(grid).also { println("Part1: $it") }
    Part2(grid).part2().also { println("Part2: $it") }
}
