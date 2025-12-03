package _2025.day3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day3 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """""".trimSplitRemoveEmptyLines()
        ) shouldBe 11
    }

    test("PartTwoExample") {
        solvePart2(
            """""".trimSplitRemoveEmptyLines()
        ) shouldBe 31
    }
})

