package me.nfekete.adventofcode.y2025.day08

import me.nfekete.adventofcode.y2025.common.Grid3D.Coord
import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.crossProduct
import me.nfekete.adventofcode.y2025.common.product

private class Part1(val boxes: List<Coord>, val nrMerges: Int) {
    fun compute(): Int {
        val distanceMap = (boxes crossProduct boxes)
            .filter { (a, b) -> a < b }
            .sortedBy { (a, b) -> (a - b).euclideanSquared }

        val sets = boxes.associateWithTo(HashMap()) { HashSet<Coord>().apply { add(it) } }
        for ((a, b) in distanceMap.take(nrMerges)) {
            val merged = sets[a]!!
            merged.addAll(sets[b]!!)
            merged.forEach { sets[it] = merged }
        }
        return sets.values.distinct().sortedByDescending { it.size }.take(3)
            .map { it.size }
            .product()
    }

}

private class Part2(val boxes: List<Coord>) {
    fun compute(): Long {
        val distanceMap = (boxes crossProduct boxes)
            .filter { (a, b) -> a < b }
            .sortedBy { (a, b) -> (a - b).euclideanSquared }

        val sets = boxes.associateWithTo(HashMap()) { HashSet<Coord>().apply { add(it) } }
        for ((a, b) in distanceMap) {
            val merged = sets[a]!!
            merged.addAll(sets[b]!!)
            if (merged.size == boxes.size)
                return a.x * b.x
            merged.forEach { sets[it] = merged }
        }
        error("")
    }

}

private fun main() {
    val boxes = classpathFile("day08/input.txt")
        .readLines()
        .map { line ->
            line.split(",").map { it.toLong() }.let { (x, y, z) -> Coord(x, y, z) }
        }

    Part1(boxes, nrMerges = 1000).compute()
        .also { println("Part1: $it") }
    Part2(boxes).compute()
        .also { println("Part2: $it") }
}
