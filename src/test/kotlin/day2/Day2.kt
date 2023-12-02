package day2

import io.kotest.assertions.asClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimAndSplit

class Day2 : FunSpec({

    test("parse line") {
        val game = parseLine("Game 1: 10 green, 9 blue, 1 red; 1 red, 7 green; 11 green, 6 blue; 8 blue, 12 green")
        game.asClue {
            it.id shouldBe 1
            it.draws[0] shouldBe ColorSet(1, 10, 9)
            it.draws[1] shouldBe ColorSet(1, 7, 0)
            it.draws[2] shouldBe ColorSet(0, 11, 6)
            it.draws[3] shouldBe ColorSet(0, 12, 8)
        }
    }

    test("Is game possible") {
        isGamePossible(ColorSet(2, 2, 2), Game(1, listOf(ColorSet(1, 1, 1)))) shouldBe true
        isGamePossible(ColorSet(0, 2, 2), Game(1, listOf(ColorSet(1, 1, 1)))) shouldBe false
        isGamePossible(ColorSet(2, 0, 2), Game(1, listOf(ColorSet(1, 1, 1)))) shouldBe false
        isGamePossible(ColorSet(2, 2, 0), Game(1, listOf(ColorSet(1, 1, 1)))) shouldBe false
    }

    test("minimum colorSet for game") {
        minimumColorSet(Game(1, listOf(ColorSet(1, 1, 1)))) shouldBe ColorSet(1, 1, 1)
        minimumColorSet(
            Game(
                1, listOf(
                    ColorSet(1, 1, 1),
                    ColorSet(2, 2, 2),
                )
            )
        ) shouldBe ColorSet(2, 2, 2)
        minimumColorSet(
            Game(
                1, listOf(
                    ColorSet(2, 1, 1),
                    ColorSet(1, 2, 1),
                    ColorSet(1, 1, 2),
                )
            )
        ) shouldBe ColorSet(2, 2, 2)
    }


    test("PartOneExample") {
        solvePart1(
            """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """.trimAndSplit(), ColorSet(12, 13, 14)
        ) shouldBe 8
    }

    test("PartTwoExample") {
        solvePart2(
            """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """.trimAndSplit()
        ) shouldBe 2286
    }
})

