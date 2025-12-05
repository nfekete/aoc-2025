package me.nfekete.adventofcode.y2025.day05

import me.nfekete.adventofcode.y2024.common.chunkBy
import me.nfekete.adventofcode.y2024.common.classpathFile
import me.nfekete.adventofcode.y2024.common.mapBoth
import me.nfekete.adventofcode.y2024.common.range
import me.nfekete.adventofcode.y2024.common.size
import me.nfekete.adventofcode.y2024.common.splitByDelimiter
import me.nfekete.adventofcode.y2024.common.union

private fun main() {
    classpathFile("day05/input.txt")
        .lineSequence()
        .chunkBy { it.isEmpty() }
        .toList()
        .let { (intervalLines, idLines) ->
            val intervals = intervalLines.map { line -> line.splitByDelimiter('-').mapBoth { it.toLong() }.range }
            val ids = idLines.map { it.toLong() }.toSet()

            ids.count { id -> intervals.any { interval -> id in interval } }
                .also { println("Part1: $it") }

            intervals.fold(emptySet<LongRange>()) { acc, longRange -> acc union longRange}
                .sumOf { it.size }
                .also { println("Part2: $it") }
        }
}
