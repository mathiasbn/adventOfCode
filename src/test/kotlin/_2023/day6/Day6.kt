package _2023.day6

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import util.parseLongList
import util.trimSplitRemoveEmptyLines

class Day6 : FunSpec({
    test("parse") {
        val races = parse(listOf(
            "Time:     5   10     15",
            "Distance:   12   14     16",
            ),String::parseLongList)
        races.races shouldHaveSize 3
        races.races[0].ms shouldBe 5
        races.races[0].bestDistance shouldBe 12
        races.races[2].ms shouldBe 15
        races.races[2].bestDistance shouldBe 16
    }

    val example = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimSplitRemoveEmptyLines()

    test("race winning range"){
        Race(3,1).winningRange() shouldBe 1L..2
        Race(4,1).winningRange() shouldBe 1L..3
        Race(4,3).winningRange() shouldBe 2L..2
    }
    test("PartOneExample") {
        solvePart1(example) shouldBe 288
    }

    test("PartTwoExample") {
        solvePart2(example) shouldBe 71503
    }
})
