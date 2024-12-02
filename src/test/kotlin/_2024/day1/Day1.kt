package _2024.day1

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day1 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """
3   4
4   3
2   5
1   3
3   9
3   3
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 11
    }



    test("PartTwoExample") {
        solvePart2(
            """
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 281
    }
})
