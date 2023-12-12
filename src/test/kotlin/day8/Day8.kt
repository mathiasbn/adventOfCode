package day8

import day8.Selection.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import util.trimSplit
import util.trimSplitRemoveEmptyLines

class Day8 : FunSpec({

    val example = """
            LLR
            
            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
    """.trimSplit()

    test("parse") {
        val (instructions, map) = parse(example)
        instructions shouldBe listOf(Left, Left, Right)

        map["AAA"] shouldBe Direction("BBB","BBB")
    }

    test("Follow instructions"){
        parse(example).followFrom("AAA") shouldBe "BBB"
        parse(example).followFrom("BBB") shouldBe "ZZZ"
    }


    test("PartOneExample") {
        solvePart1(example) shouldBe 6
    }

    test("PartTwoExample") {
        solvePart2(example) shouldBe 5905
    }
})
