package _2024.day3

import util.readInputLines

private val mulExpression = Regex("mul\\((?<first>\\d{1,3}),(?<second>\\d{1,3})\\)")
fun solvePart1(input: List<String>): Int =
    input.flatMap { line ->
        mulExpression.findAll(line)
            .map { evaluateMul(it) }
    }.sum()

private fun evaluateMul(it: MatchResult) = it.groups["first"]!!.value.toInt() * it.groups["second"]!!.value.toInt()

private val doDontMulExpression = Regex("(?<do>do\\(\\))|(?<doNot>don't\\(\\))|mul\\((?<first>\\d{1,3}),(?<second>\\d{1,3})\\)")
fun solvePart2(input: List<String>): Int = input.joinToString("")
    .let { doDontMulExpression.findAll(it) }
    .fold(Pair(0,true)){acc, match ->
        when {
            match.groups["doNot"] != null -> Pair(acc.first, false)
            match.groups["do"] != null -> Pair(acc.first, true)
            match.groups["first"] != null -> if(acc.second) Pair(acc.first+ evaluateMul(match), true) else acc
            else -> throw Exception()
        }
    }.first

fun main() {
    solvePart1(
        readInputLines("_2024/day3_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2024/day3_part1")
    )
        .also { println("Part two result: $it") }
}