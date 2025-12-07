package me.nfekete.adventofcode.y2025.day05

import me.nfekete.adventofcode.y2025.common.chunkBy
import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.mapBoth
import me.nfekete.adventofcode.y2025.common.range
import me.nfekete.adventofcode.y2025.common.size
import me.nfekete.adventofcode.y2025.common.splitByDelimiter
import me.nfekete.adventofcode.y2025.common.union
import kotlin.time.measureTimedValue

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

//            repeat(10000) {
                measureTimedValue {
                    intervals.fold(emptySet<LongRange>()) { acc, longRange -> acc union longRange }
                        .sumOf { it.size }
                }
                    .also { println("Part2: $it") }
//            }
        }
}
