package _2024.day5

import util.*

fun solvePart1(input: List<String>): Int {
    val rules = parseRules(input)
    val updates = parseUpdates(input)
    return updates.filter { isCorrectOrder(it, rules) }.sumOf { middlePageNumber(it) }
}

fun solvePart2(input: List<String>): Int {
    val rules = parseRules(input)
    val updates = parseUpdates(input)
    return updates
        .filter { !isCorrectOrder(it, rules) }
        .map { it.sortedWith(pageComparator(rules)) }
        .sumOf { middlePageNumber(it) }
}

fun parseRules(rulesAndUpdates: List<String>): Map<Pair<Int, Int>, Int> =
    rulesAndUpdates
        .takeWhile { it.isNotBlank() }
        .fold(mapOf()) { acc, ruleText ->
            val (before, after) = ruleText.split("|").map(String::toInt)
            acc + ((before to after) to -1) + ((after to before) to 1)
        }

fun parseUpdates(rulesAndUpdates: List<String>): List<List<Int>> =
    rulesAndUpdates
        .dropWhile { it.isNotBlank() }.drop(1)
        .map { update -> update.split(",").map { it.toInt() } }

fun isCorrectOrder(update: List<Int>, rules: Map<Pair<Int, Int>, Int>) =
    update == update.sortedWith(pageComparator(rules))

fun pageComparator(rules: Map<Pair<Int, Int>, Int>) = Comparator<Int> { o1, o2 -> rules[o1 to o2] ?: 0 }

fun middlePageNumber(update: List<Int>) = update[(update.size / 2)]

fun main() {
    solvePart1(
        readInputLines("_2024/day5_part1", allowEmptyLines = true)
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2024/day5_part1", allowEmptyLines = true)
    )
        .also { println("Part two result: $it") }
}
