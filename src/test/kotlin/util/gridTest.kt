package util

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GridTest : FunSpec({

    test("Creation of lines"){
        (Point(0,0) undirectedLineTo Point(1,0)).point1 shouldBe Point(0,0)
        (Point(1,0) undirectedLineTo Point(0,0)).point1 shouldBe Point(0,0)
    }

    test("Vectors of line") {
        (Point(0,0) undirectedLineTo Point(1,0)).vector() shouldBe Point(1,0)
        (Point(5,5) undirectedLineTo Point(6,5)).vector() shouldBe Point(1,0)
        (Point(0,5) undirectedLineTo Point(1,4)).vector() shouldBe Point(1,-1)
    }
    test("Reverse vector") {
        Point(1,0).reverseVector() shouldBe Point(-1,0)
        Point(-5,3).reverseVector() shouldBe Point(5,-3)
    }

    test("Step vector") {
        Point(0,0).step(Point(2,3)) shouldBe Point(2,3)
        Point(1,4).step(Point(1,-1)) shouldBe Point(2,3)
    }
})