package me.nfekete.adventofcode.y2025.day06

import me.nfekete.adventofcode.y2025.common.chunkBy
import me.nfekete.adventofcode.y2025.common.classpathFile
import me.nfekete.adventofcode.y2025.common.product
import me.nfekete.adventofcode.y2025.common.transpose

private val splitRegex = Regex(" +")
private fun main() {
    val lines = classpathFile("day06/input.txt")
        .readLines()
    val operandLines = lines.dropLast(1)
    val operators = lines.last().trim().split(splitRegex)
    run {
        val operands = operandLines.map { line -> line.trim().split(splitRegex).map { it.toLong() } }
        operators.mapIndexed { index, operator ->
            when (operator) {
                "+" -> operands.sumOf { it[index] }
                "*" -> operands.map { it[index] }.product()
                else -> error("Unknown operator '$operator'")
            }
        }.sum().also { println("Part1: $it") }
    }

    run {
        val maxLength = operandLines.maxOf { it.length }
        val paddedLines = operandLines.map { line -> line.padEnd(maxLength).map { it } }
        paddedLines.transpose()
            .map { it.joinToString(separator = "") }
            .asSequence()
            .chunkBy { it.isBlank() }
            .toList()
            .mapIndexed { index, strings ->
                val operands = strings.map { it.trim().toLong() }
                when (val operator = operators[index]) {
                    "+" -> operands.sum()
                    "*" -> operands.product()
                    else -> error("Unknown operator '$operator'")
                }
            }
            .sum()
            .let { println("Part2: $it") }
    }

}
