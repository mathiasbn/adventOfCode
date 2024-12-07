package _2024.day7

import _2024.day7.Operator.*
import util.*

enum class Operator { ADD, MUL, CONCAT }
data class PartialEquation(val result: Long, val operands: List<Long>) {
    fun hasResult(includeConcat: Boolean) = possibleOperatorPermutations(operands.size - 1, includeConcat)
        .any { applyOperatorsToOperands(it, operands) == result }
}

fun solvePart1(input: List<String>) = parseCalibrationEquations(input)
    .filter { it.hasResult(false) }
    .sumOf { it.result }

fun parseCalibrationEquations(equations: List<String>) = equations.map {
    val (result, operands) = it.split(": ")
    PartialEquation(result.toLong(), operands.split(" ").map(String::toLong))
}

fun possibleOperatorPermutations(numOfOperators: Int, includeConcat: Boolean): Set<List<Operator>> {
    val sets =
        (0 until numOfOperators).map { listOf(ADD, MUL) + if (includeConcat) listOf(CONCAT) else emptySet() }.toList()
    return cartesianProduct(sets)
}

fun <T> cartesianProduct(sets: List<List<T>>) =
    sets.fold(listOf(listOf<T>())) { acc, set ->
        acc.flatMap { list -> set.map { element -> list + element } }
    }.toSet()

fun applyOperatorsToOperands(operator: List<Operator>, operands: List<Long>) = operands
    .reduceIndexed { index: Int, acc: Long, value: Long ->
        when (operator[index - 1]) {
            ADD -> acc + value
            MUL -> acc * value
            CONCAT -> (acc.toString() + value.toString()).toLong()
        }
    }


fun solvePart2(input: List<String>) = parseCalibrationEquations(input)
    .filter { it.hasResult(true) }
    .sumOf { it.result }


fun main() {
    solvePart1(
        readInputLines("_2024/day7_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2024/day7_part1")
    )
        .also { println("Part two result: $it") }
}
