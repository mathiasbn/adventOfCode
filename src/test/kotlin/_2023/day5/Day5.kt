package _2023.day5

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import util.trimSplit

class Day5 : FunSpec({
    val example = """
            seeds: 79 14 55 13

            seed-to-soil map:
            50 98 2
            52 50 48

            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15

            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4

            water-to-light map:
            88 18 7
            18 25 70

            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13

            temperature-to-humidity map:
            0 69 1
            1 0 69

            humidity-to-location map:
            60 56 37
            56 93 4
        """

    test("parse") {
        val almanac = parse(example.trimSplit()) { map { it..it } }
        almanac.seeds shouldBe listOf(79L..79, 14L..14, 55L..55, 13L..13)
        almanac.seedToSoil[98] shouldBe 50
        almanac.soilToFertilizer[52] shouldBe 37
        almanac.fertilizerToWater[0] shouldBe 42
        almanac.waterToLight[25] shouldBe 18
        almanac.lightToTemperature[45] shouldBe 81
        almanac.temperatureToHumidity[0] shouldBe 1
        almanac.humidityToLocation[93] shouldBe 56
    }

    test("DestSourceRange") {
        val range = DestSourceRange(0, 0, 2)
        (0 in range) shouldBe true
        range[0]
        (1 in range) shouldBe true
        range[1]
        (2 in range) shouldBe false
        shouldThrow<Exception> { range[2] }

        val range2 = DestSourceRange(13, 45, 15)
        (45 in range2) shouldBe true
        (59 in range2) shouldBe true
        (60 in range2) shouldBe false
        range2[47] shouldBe 15
        range2[59] shouldBe 27
        shouldThrow<Exception> { range2[60] }
    }

    test("CategoryConversion") {
        val categoryConversion = CategoryConversion(listOf(DestSourceRange(1, 0, 2)))
        categoryConversion[0] shouldBe 1
        categoryConversion[1] shouldBe 2
        categoryConversion[2] shouldBe 2

        val categoryConversion2 = CategoryConversion(
            listOf(
                DestSourceRange(1, 0, 2),
                DestSourceRange(20, 2, 2)
            )
        )
        categoryConversion2[1] shouldBe 2
        categoryConversion2[2] shouldBe 20
        categoryConversion2[3] shouldBe 21
        categoryConversion2[4] shouldBe 4
    }

    test("find location") {
        val almanac = Almanac(
            listOf(0L..0L, 1L..1L),
            CategoryConversion(listOf(DestSourceRange(2, 1, 1))),
            CategoryConversion(listOf(DestSourceRange(3, 2, 1))),
            CategoryConversion(listOf(DestSourceRange(4, 3, 1))),
            CategoryConversion(listOf(DestSourceRange(5, 4, 1))),
            CategoryConversion(listOf(DestSourceRange(6, 5, 1))),
            CategoryConversion(listOf(DestSourceRange(7, 6, 1))),
            CategoryConversion(listOf(DestSourceRange(8, 7, 1)))
        )
        almanac.seedToLocation(0L..0).single() shouldBe 0L..0
        almanac.seedToLocation(1L..1).single() shouldBe 8L..8
        almanac.seedLocations() shouldBe mapOf(Pair(0L..0, listOf(0L..0)), Pair(1L..1, listOf(8L..8)))
    }

    test("PartOneExample") {
        solvePart1(example.trimSplit()) shouldBe 35
    }

    test("PartTwoExample") {
        solvePart2(example.trimSplit()) shouldBe 46
    }
})
