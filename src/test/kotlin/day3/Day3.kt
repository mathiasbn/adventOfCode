package day3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import util.trimAndSplit

class Day3 : FunSpec({

    test("parse line") {
        val potentialParts = findPotentialParts(0, ".123..1...666")
        potentialParts shouldContain PotentialPart(123, Point(1, 0))
        potentialParts shouldContain PotentialPart(1, Point(6, 0))
        potentialParts shouldContain PotentialPart(666, Point(10, 0))
    }

    test("Adjacent points") {
        adjacentPoints(PotentialPart(1, Point(1, 1))) shouldBe setOf(
            Point(0, 0), Point(1, 0), Point(2, 0),
            Point(0, 1), Point(2, 1),
            Point(0, 2), Point(1, 2), Point(2, 2)
        )

        adjacentPoints(PotentialPart(1, Point(5, 5))) shouldBe setOf(
            Point(4, 4), Point(5, 4), Point(6, 4),
            Point(4, 5), Point(6, 5),
            Point(4, 6), Point(5, 6), Point(6, 6)
        )

        adjacentPoints(PotentialPart(42, Point(1, 1))) shouldBe setOf(
            Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0),
            Point(0, 1), Point(3, 1),
            Point(0, 2), Point(1, 2), Point(2, 2), Point(3, 2)
        )
    }

    test("part determines") {
        partDeterminedPoints(
            """
            /....
            ..*..
            ...._
        """.trimAndSplit()
        ) shouldBe setOf(
            Point(0, 0), Point(2, 1), Point(4, 2)
        )
    }

    test("find gear") {
        val potentialGears = setOf(Point(1, 0))
        val partNumbers = setOf(PotentialPart(2, Point(0, 0)), PotentialPart(3, Point(2, 0)))
        findGears(potentialGears, partNumbers) shouldBe setOf(2 * 3)

        val potentialGears2 = setOf(Point(1, 0))
        val partNumbers2 = setOf(PotentialPart(3, Point(2, 0)))
        findGears(potentialGears2, partNumbers2) shouldBe emptySet()
    }

    test("PartOneExample") {
        solvePart1(
            """
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
..._.*....
.664.598..                
            """.trimAndSplit()
        ) shouldBe 4361
    }

    test("PartTwoExample") {
        solvePart2(
            """
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...-.*....
.664.598..
            """.trimAndSplit()
        ) shouldBe 467835
    }
})
