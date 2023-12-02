package day2

import util.readInputLines

fun solvePart1(input: List<String>, bagContent: ColorSet): Int {
    return input.map { parseLine(it) }
        .filter { isGamePossible(bagContent, it) }
        .map { it.id }
        .sum()
}

fun solvePart2(input: List<String>) =
    input.map { parseLine(it) }
        .map { minimumColorSet(it) }
        .sumOf { it.red*it.green*it.blue }

data class ColorSet(val red: Int, val green: Int, val blue: Int)

fun parseLine(line: String): Game {
    val (gameHeader, rest) = line.split(":")
    val gameId = gameHeader.removePrefix("Game ").toInt()
    val draws = rest.split(";")
        .map { draw ->
            draw.split(",")
                .map { it.trim() }
                .map { color ->
                    when {
                        color.endsWith("red") -> Pair("red", color.removeSuffix(" red").toInt())
                        color.endsWith("green") -> Pair("green", color.removeSuffix(" green").toInt())
                        color.endsWith("blue") -> Pair("blue", color.removeSuffix(" blue").toInt())
                        else -> throw Error("Unexpected color: $color")
                    }
                }
                .let { colors ->
                    ColorSet(
                        colors.find { it.first == "red" }?.second ?: 0,
                        colors.find { it.first == "green" }?.second ?: 0,
                        colors.find { it.first == "blue" }?.second ?: 0,
                    )
                }
        }
    return Game(gameId, draws)
}

fun isGamePossible(bagContent: ColorSet, game: Game): Boolean {
    return game.draws.all {
        it.red <= bagContent.red &&
                it.green <= bagContent.green &&
                it.blue <= bagContent.blue
    }
}

fun minimumColorSet(game: Game) = ColorSet(
    game.draws.maxOf { it.red },
    game.draws.maxOf { it.green },
    game.draws.maxOf { it.blue },
)
data class Game(val id: Int, val draws: List<ColorSet>)

fun main() {
    println("Day2\n")
    solvePart1(
        readInputLines("day2_part1"),
        ColorSet(12, 13, 14)
    )
        .also { println("Part one result: $it") }

    solvePart2(
        readInputLines("day2_part1")
    )
        .also { println("Part two result: $it") }
}

