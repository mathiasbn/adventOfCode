package day7

import util.readInputLines

fun solvePart1(input: List<String>): Int {
    return parse(input, false).sorted().mapIndexed { i, handBid -> (i + 1) * handBid.bid }.sum()
}

fun solvePart2(input: List<String>): Int {
    return parse(input, true).sorted().mapIndexed { i, handBid -> (i + 1) * handBid.bid }.sum()
}


fun parse(input: List<String>, newRules: Boolean) =
    input.map { it.split(" ").let { (hand, bid) -> HandBid(hand, bid.toInt(), newRules) } }

data class HandBid(val cards: String, val bid: Int, val newRules: Boolean) : Comparable<HandBid> {
    val hand = Hand(cards, newRules)
    override fun compareTo(other: HandBid) = hand.compareTo(other.hand)
}

data class Hand(val cards: String, val newRules: Boolean = false) : Comparable<Hand> {
    val type = if (newRules) {
        cards.groupBy { it }.filterKeys { it != 'J' }.values
            .let { sameCardGroups ->
                val jokerCount = cards.count { it == 'J' }
                val (most, mostChar) = sameCardGroups.maxByOrNull { it.size }.let { Pair(it?.size?:0, it?.first()) }
                when {
                    most + jokerCount == 5 -> 7
                    most + jokerCount == 4 -> 6
                    most + jokerCount == 3 && sameCardGroups.filter { it.first() != mostChar }.any { it.size == 2 } -> 5
                    most + jokerCount == 3 -> 4
                    sameCardGroups.count { it.size == 2 } == 2 -> 3
                    most + jokerCount == 2 -> 2
                    else -> 1
                }
            }
    } else {
        cards.groupBy { it }.values
            .let { sameCardGroups ->
                val most = sameCardGroups.maxOfOrNull { it.size }!!
                when {
                    most == 5 -> 7
                    most == 4 -> 6
                    most == 3 && sameCardGroups.any { it.size == 2 } -> 5
                    most == 3 -> 4
                    sameCardGroups.count { it.size == 2 } == 2 -> 3
                    most == 2 -> 2
                    else -> 1
                }
            }
    }

    val cardValues = cards.map {
        when (it) {
            'T' -> 10
            'J' -> if (newRules) 1 else 11
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> it.digitToInt()
        }
    }

    override fun compareTo(other: Hand) = when {
        type != other.type -> type.compareTo(other.type)
        cards != other.cards -> cardValues.zip(other.cardValues).map { (a, b) -> a.compareTo(b) }.find { it != 0 } ?: 0
        else -> 0
    }
}


fun main() {
    println("Day7\n")
    solvePart1(
        readInputLines("day7_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("day7_part1")
    )
        .also { println("Part two result: $it") }
}
