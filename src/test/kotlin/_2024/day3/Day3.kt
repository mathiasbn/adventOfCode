package _2024.day3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day3 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""".trimSplitRemoveEmptyLines()
        ) shouldBe 161
    }

    test("PartTwoExample") {
        solvePart2(
            """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""".trimSplitRemoveEmptyLines()
        ) shouldBe 48
    }
})