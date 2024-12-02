package _2023.day4

import util.readInputLines
import kotlin.math.pow

fun solvePart1(input: List<String>) = input.map(::parseLine).map { it.calculatePrize() }.sum()

fun solvePart2(input: List<String>) = resolveReceivedCards(input.map(::parseLine))

fun parseLine(card: String): ScratchCard {
    val (title, rest) = card.split(":")
    val (playerNumbers, winnerNumbers) = rest.split("|")
    return ScratchCard(
        "Card\\s+(?<cardNumber>\\d+)".toRegex().find(title)!!.groups["cardNumber"]!!.value.toInt(),
        playerNumbers.split(" ").filter(String::isNotBlank).map { it.trim().toInt() }.toSet(),
        winnerNumbers.split(" ").filter(String::isNotBlank).map { it.trim().toInt() }.toSet()
    )
}

fun resolveReceivedCards(cards: List<ScratchCard>): Int {
    val cardCounter = cards.associate { it.cardNumber to 1 }.toMutableMap()
    cards.forEach { card ->
        if (card.playerWinningNumbers() > 0) {
            (1..card.playerWinningNumbers())
                .forEach {
                    cardCounter[card.cardNumber + it] = cardCounter[card.cardNumber + it]!! + cardCounter[card.cardNumber]!!
                }
        }
    }
    return cardCounter.values.sum()
}

data class ScratchCard(val cardNumber: Int, val playerNumbers: Set<Int>, val winnerNumbers: Set<Int>) {
    fun calculatePrize(): Int {
        return playerWinningNumbers()
            .let { 2.0.pow(it - 1).toInt() }
    }

    fun playerWinningNumbers() = playerNumbers.intersect(winnerNumbers).size
}

fun main() {
    println("Day4\n")
    solvePart1(
        readInputLines("day4_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("day4_part1")
    )
        .also { println("Part two result: $it") }
}
