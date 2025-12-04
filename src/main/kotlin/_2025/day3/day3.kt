package _2025.day3

import util.readInputLines

fun highestJoltage(bank: String) = highestJoltage(bank, 2).toInt()

fun highestJoltage(bank: String, amountOfBatteries: Int) = highestJoltageRecursive(bank, amountOfBatteries,"").toLong()

private tailrec fun highestJoltageRecursive(bank: String, amountOfBatteries: Int, current: String): String {
    if (amountOfBatteries == 0) return current
    val nextBattery = bank.dropLast(amountOfBatteries - 1).maxBy { it.digitToInt() }.digitToInt()
    val remainingBank = bank.dropWhile { it.toString() != nextBattery.toString() }.drop(1)
    return highestJoltageRecursive(remainingBank, amountOfBatteries - 1, "$current$nextBattery")
}

fun solvePart1(input: List<String>): Int = input.sumOf(::highestJoltage)

fun solvePart2(input: List<String>): Long = input.sumOf { highestJoltage(it, 12) }


fun main() {
    solvePart1(
        readInputLines("_2025/day3_part1")
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("_2025/day3_part1")
    )
        .also { println("Part two result: $it") }
}