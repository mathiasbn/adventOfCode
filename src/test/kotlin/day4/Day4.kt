package day4

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplitRemoveEmptyLines

class Day4 : FunSpec({
    test("parsing") {
        val game = parseLine("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")
        game.cardNumber shouldBe 1
        game.playerNumbers shouldBe setOf(41, 48, 83, 86, 17)
        game.winnerNumbers shouldBe setOf(83, 86, 6, 31, 17, 9, 48, 53)
        parseLine("Card  1: 2 | 4").cardNumber shouldBe 1
        parseLine("Card 10: 2 | 4").cardNumber shouldBe 10
    }

    test("card calculation") {
        ScratchCard(1, setOf(1), setOf(2)).calculatePrize() shouldBe 0
        ScratchCard(1, setOf(1), setOf(1)).calculatePrize() shouldBe 1
        ScratchCard(1, setOf(1, 2), setOf(1, 2)).calculatePrize() shouldBe 2
        ScratchCard(1, setOf(1, 2, 3), setOf(1, 2, 3)).calculatePrize() shouldBe 4
    }

    test("card deck copies") {
        resolveReceivedCards(listOf(ScratchCard(1, setOf(1), setOf(2))))
            .shouldBe(1)
        resolveReceivedCards(
            listOf(
                ScratchCard(1, setOf(1), setOf(1)),
                ScratchCard(2, setOf(1), setOf(2))
            )
        ) shouldBe 3
        resolveReceivedCards(
            listOf(
                ScratchCard(1, setOf(1,666), setOf(1,666)),
                ScratchCard(2, setOf(42), setOf(43)),
                ScratchCard(3, setOf(1), setOf(2))
            )
        ) shouldBe 5
        resolveReceivedCards(
            listOf(
                ScratchCard(1, setOf(1,666), setOf(1,666)),
                ScratchCard(2, setOf(42), setOf(42)),
                ScratchCard(3, setOf(1), setOf(2))
            )
        ) shouldBe 7
    }

    test("PartOneExample") {
        solvePart1(
            """
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 13
    }

    test("PartTwoExample") {
        solvePart2(
            """
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
            """.trimSplitRemoveEmptyLines()
        ) shouldBe 30
    }
})