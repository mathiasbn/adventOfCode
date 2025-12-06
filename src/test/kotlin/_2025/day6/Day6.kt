package _2025.day6

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day6 : FunSpec({
    test("partOneExample") {
        solvePart1(
            """
                123 328  51 64 
                 45 64  387 23 
                  6 98  215 314
                *   +   *   +  
            """.trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 4277556
    }

    test("PartTwoExample") {
        solvePart2(
            """
123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  
            """.trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 3263827
    }
})

