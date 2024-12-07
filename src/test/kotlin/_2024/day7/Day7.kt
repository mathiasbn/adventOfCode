package _2024.day7

import _2024.day7.Operator.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.*

class Day7 : FunSpec({

    test("Apply operators to numbers") {
        applyOperatorsToOperands(listOf(ADD, ADD, MUL), listOf(1, 2, 3, 4)) shouldBe 24
        applyOperatorsToOperands(listOf(ADD, MUL, ADD), listOf(1, 2, 3, 4)) shouldBe 13
    }

    test("Permutations of operators") {
        possibleOperatorPermutations(1,false) shouldBe setOf(listOf(ADD), listOf(MUL))
        possibleOperatorPermutations(1,true) shouldBe setOf(listOf(ADD), listOf(MUL), listOf(CONCAT))
        possibleOperatorPermutations(2, false) shouldBe setOf(
            listOf(ADD, ADD),
            listOf(MUL, ADD),
            listOf(ADD, MUL),
            listOf(MUL, MUL)
        )
    }

    test("Parse") {
        parseCalibrationEquations(listOf("190: 10 19"))[0] shouldBe PartialEquation(190, listOf(10, 19))
    }

    test("Has result") {
        PartialEquation(190, listOf(10, 19)).hasResult(false) shouldBe true
        PartialEquation(83, listOf(17, 5)).hasResult(false) shouldBe false
        PartialEquation(156, listOf(15, 6)).hasResult(false) shouldBe false
        PartialEquation(156, listOf(15, 6)).hasResult(true) shouldBe true

    }

    test("partOneExample") {
        solvePart1(
            """
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20
            """.trimIndent().trimSplit()
        ) shouldBe 3749
    }

    test("PartTwoExample") {
        solvePart2(
            """
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20
            """.trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 11387
    }
})