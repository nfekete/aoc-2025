package me.nfekete.adventofcode.y2025.day10

import me.nfekete.adventofcode.y2025.common.classpathFile

private data class Problem(val bulbs: Long, val buttons: List<Long>, val joltages: List<Int>) {
    fun part1(): Int? {
        fun recurse(bulbs: Long, fromIndex: Int, bitsOn: Int): Int? =
            if (bulbs == 0L)
                bitsOn
            else if (fromIndex >= buttons.size)
                null
            else
                listOfNotNull(
                    recurse(bulbs.xor(buttons[fromIndex]), fromIndex + 1, bitsOn + 1),
                    recurse(bulbs, fromIndex + 1, bitsOn),
                ).minOrNull()

        return recurse(bulbs, 0, 0)
    }
}

private fun main() {
    val input = classpathFile("day10/input.txt")
        .readLines()
        .map { line ->
            val split = line.split(" ")
            val bulbsConfiguration = split.first().removeSurrounding("[", "]").map { if (it == '#') 1L else 0L }
            val bulbs = bulbsConfiguration.foldRight(0L) { bit, acc -> acc.shl(1).or(bit) }
            val buttons = split.drop(1).dropLast(1).map { it.removeSurrounding("(", ")") }
                .map { s ->
                    s.split(",").map { it.toInt() }.let { lights ->
                        lights.fold(0L) { acc, i -> acc.or(1L.shl(i)) }
                    }
                }
            val joltages = split.last().removeSurrounding("{", "}").split(",").map { it.toInt() }
            Problem(bulbs, buttons, joltages)
        }

    input.sumOf { it.part1()!! }.also { println("Part1: $it") }
}
