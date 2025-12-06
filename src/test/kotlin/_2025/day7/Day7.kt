package _2025.day7

import _2025.day6.solvePart1
import _2025.day6.solvePart2
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day7 : FunSpec({
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

