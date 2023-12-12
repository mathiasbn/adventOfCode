package day7

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day7 : FunSpec({

    val example = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
    """.trimSplitRemoveEmptyLines()

    test("parse") {
        val hands = parse(example, false)
        hands[0].cards shouldBe "32T3K"
        hands[4].bid shouldBe 483
    }

    test("Hand type"){
        Hand("22222").type shouldBe 7
        Hand("22223").type shouldBe 6
        Hand("22233").type shouldBe 5
        Hand("22213").type shouldBe 4
        Hand("22331").type shouldBe 3
        Hand("22134").type shouldBe 2
        Hand("12345").type shouldBe 1

        Hand("JJJJJ",true).type shouldBe 7
        Hand("JKJJJ",true).type shouldBe 7
        Hand("JKKJJ",true).type shouldBe 7
        Hand("JKKKJ",true).type shouldBe 7
        Hand("JKKKK",true).type shouldBe 7
        Hand("JKKKK",true).type shouldBe 7
        Hand("JKKQQ",true).type shouldBe 5
        Hand("JJKTQ",true).type shouldBe 4
        Hand("JTKQQ",true).type shouldBe 4
    }
    test("Order"){
        Hand("12345") shouldBeLessThan Hand("54321")
        Hand("11345") shouldBeGreaterThan Hand("54321")
        Hand("12345") shouldBeLessThan  Hand("TJQKA")
        Hand("TJQKA") shouldBeLessThan  Hand("AKQJT")
        Hand("TTJQK") shouldBeLessThan  Hand("TTAQK")

        Hand("JKKKK", true) shouldBeLessThan  Hand("2QQQQ", newRules = true)
    }

    test("PartOneExample") {
        solvePart1(example) shouldBe 6440
    }

    test("PartTwoExample") {
        solvePart2(example) shouldBe 5905
    }
}) {
}
