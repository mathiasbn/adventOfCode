package _2024.day8

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.*

class Day8 : FunSpec({

    test("Find antenna pairs") {
        findSameFrequencyPairs(
            Grid(
        """
            A..A
            .A..
            .b.b
        """.trimSplit()
            )
        ) shouldBe setOf(
            Point(0, 0) undirectedLineTo  Point(3, 0),
            Point(0, 0) undirectedLineTo  Point(1, 1),
            Point(1, 1) undirectedLineTo  Point(3, 0),
            Point(1, 2) undirectedLineTo  Point(3, 2),
        )
    }

    test("partOneExample") {
        solvePart1(
            """
                ............
                ........0...
                .....0......
                .......0....
                ....0.......
                ......A.....
                ............
                ............
                ........A...
                .........A..
                ............
                ............
            """.trimIndent().trimSplit()
        ) shouldBe 14
    }

    test("PartTwoExample") {
        solvePart2(
            """
                ............
                ........0...
                .....0......
                .......0....
                ....0.......
                ......A.....
                ............
                ............
                ........A...
                .........A..
                ............
                ............
            """.trimIndent().trimSplitRemoveEmptyLines()
        ) shouldBe 34
    }
})

