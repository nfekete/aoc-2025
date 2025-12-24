package me.nfekete.adventofcode.y2025.day10

import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.map2
import me.nfekete.adventofcode.y2025.common.memoized

private data class Problem(val bulbs: Long, val buttons: List<Set<Int>>, val joltages: List<Int>) {
    val buttonBits: List<Long> = buttons.map { lights ->
        lights.lightsAsLong()
    }

    private fun Set<Int>.lightsAsLong(): Long = fold(0L) { acc, i -> acc.or(1L.shl(i)) }

    fun part1(): Int? {
        fun recurse(bulbs: Long, fromIndex: Int, bitsOn: Int): Int? =
            if (bulbs == 0L)
                bitsOn
            else if (fromIndex >= buttonBits.size)
                null
            else
                listOfNotNull(
                    recurse(bulbs.xor(buttonBits[fromIndex]), fromIndex + 1, bitsOn + 1),
                    recurse(bulbs, fromIndex + 1, bitsOn),
                ).minOrNull()

        return recurse(bulbs, 0, 0)
    }

    fun bulbSolvingConfigurations(bulbs: Long, fromIndex: Int, set: Set<Int>): Set<Set<Int>> =
        if (fromIndex >= buttonBits.size)
            if (bulbs == 0L)
                setOf(set)
            else
                emptySet()
        else
            listOfNotNull(
                bulbSolvingConfigurations(bulbs.xor(buttonBits[fromIndex]), fromIndex + 1, set + fromIndex),
                bulbSolvingConfigurations(bulbs, fromIndex + 1, set),
            ).flatten().toSet()

    fun Set<Int>.buttonsToLights() = flatMap { buttons[it] }

    fun recurseP2(joltages: List<Int>): Int? =
        if (joltages.all { it == 0 })
            0
        else {
            val joltageParity = joltages.map { it.mod(2).toLong() }.foldRight(0L) { i, acc -> acc.shl(1).or(i) }
            val solvingConfigurations = bulbSolvingConfigurations(joltageParity, 0, emptySet())
            solvingConfigurations.map { buttons ->
                buttons.size to buttons.buttonsToLights().fold(joltages) { acc, buttonIndex ->
                    acc.mapIndexed { joltageIndex, joltage -> if (joltageIndex == buttonIndex) joltage.dec() else joltage }
                }
            }.filter { (_, joltages) -> joltages.all { joltage -> joltage >= 0 } }
                .map { pair -> pair.map2 { joltages -> joltages.map { it / 2 } } }.mapNotNull { (size, map) ->
                    recurseP2M(map)?.let { size + 2 * it }
                }.minOrNull()
        }

    val recurseP2M = ::recurseP2.memoized()
    fun part2() = recurseP2M(joltages)!!
}

private fun <A, B, C, R> Triple<A, B, C>.map(mapper: (A, B, C) -> R) = mapper(first, second, third)

private fun main() {
    val input = classpathFile("day10/input.txt")
        .readLines()
        .map { line ->
            val split = line.split(" ")
            val bulbsConfiguration = split.first().removeSurrounding("[", "]").map { if (it == '#') 1L else 0L }
            val bulbs = bulbsConfiguration.foldRight(0L) { bit, acc -> acc.shl(1).or(bit) }
            val buttons = split.drop(1).dropLast(1).map { it.removeSurrounding("(", ")") }.map { s ->
                s.split(",").map { it.toInt() }.toSet()
            }
            val joltages = split.last().removeSurrounding("{", "}").split(",").map { it.toInt() }
            Triple(bulbs, buttons, joltages)
        }

    input.map { it.map(::Problem) }.sumOf { it.part1()!! }.also { println("Part1: $it") }
    input.asSequence().map { it.map(::Problem).part2() }.onEach { println(it) }.sum().also { println("Part2: $it") }
}
