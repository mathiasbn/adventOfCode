package _2024.day1

import util.readInputLines

fun solvePart1(input: List<String>): Int =
    splitTextColumns(input).map { it.sorted() }
        .let { it[0].zip(it[1]) }
        .map { Math.abs(it.first - it.second) }
        .sum()

fun solvePart2(input: List<String>): Int =
    splitTextColumns(input)
        .let { Pair(it[0], it[1].groupBy { it }.mapValues { it.value.size }) }
        .let { it.first.map { num -> num * (it.second[num] ?: 0) } }
        .sum()


fun splitTextColumns(input: List<String>, columns: Int = 2): List<List<Int>> =
    input.map { it.split("   ").map { num -> num.toInt() } }
        .fold((1..columns).map { mutableListOf() }) { acc: List<MutableList<Int>>, ints ->
            acc.also { ints.forEachIndexed { index: Int, num: Int -> acc[index].add(num) } }
        }

fun main() {
    solvePart1(
        readInputLines("_2024/day1_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2024/day1_part1")
    )
        .also { println("Part two result: $it") }
}