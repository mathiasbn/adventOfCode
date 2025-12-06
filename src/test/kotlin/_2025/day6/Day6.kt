package _2025.day6

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day6 : FunSpec({
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

