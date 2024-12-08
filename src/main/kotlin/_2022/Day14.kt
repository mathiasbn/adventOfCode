
import util.Direction
import util.Direction.*
import util.Line
import util.Point
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

data class Cave(val walls: List<Line>, val implicitFloor: Boolean = false) {
    val implicitFloorLevel: Int = walls.flatten().map { it.y }.max() + 2
    val occupiedSpace: MutableMap<Int, MutableList<Int>> =
        walls.flatten().groupBy({ it.x }, { it.y }).let { it.mapValues { it.value.toMutableList() }.toMutableMap() }

    fun wallAt(point: Point): Boolean {
        return walls.any { it.contains(point) }
    }

    fun dropSand(): Point? {
        return dropSandFrom(Point(500, 0))?.also { occupiedSpace.getOrPut(it.x, { mutableListOf() }) += it.y }
    }

    private tailrec fun dropSandFrom(point: Point): Point? {
        val occupiedSpaceDownFrom =
            { point: Point -> occupiedSpace[point.x].let { if (implicitFloor) (it?: listOf()) + implicitFloorLevel else it } }
        val streightDown = occupiedSpaceDownFrom(point)
            ?.filter { it > point.y }
            ?.minByOrNull { it }?.let { Point(point.x, it - 1) }
            ?: return null.also {
                if (implicitFloor) {
                    println(
                        "Didn't expect to fall through with implicit floor. " +
                                "Searched for point below $point, with occupied: ${occupiedSpaceDownFrom(point)}. " +
                                "It looks like: \n"
                    )
                    print()
                    throw Exception()
                }
            }
        if (occupiedSpaceDownFrom(streightDown.step(DL))?.contains(streightDown.step(DL).y) != true)
            return dropSandFrom(streightDown.step(DL))
        else if (occupiedSpaceDownFrom(streightDown.step(DR))?.contains(streightDown.step(DR).y) != true)
            return dropSandFrom(streightDown.step(DR))
        return streightDown
    }

    fun print() {
        val xmin = occupiedSpace.keys.min()
        val xmax = occupiedSpace.keys.max()
        val ymin = occupiedSpace.values.flatten().min()
        val ymax = occupiedSpace.values.flatten().max()

        print((-1).toString().padStart(5))
        (xmin..xmax).forEach {
            if (it == 500) print("|")
            else print(" ")
        }
        println()
        (ymin..ymax).map { y ->
            print(y.toString().padStart(5))
            (xmin..xmax).map { x ->
                if (occupiedSpace[x]?.contains(y) != true) {
                    print(" ")
                } else if (wallAt(Point(x, y))) {
                    print("#")
                } else
                    print("•")
            }
            println()
        }
    }

}

fun buildCave(input: String, implicitFloor: Boolean = false): Cave {
    return Cave(input.lines().filter { it.isNotBlank() }.flatMap { wallSegment ->
        wallSegment.split(" -> ").map { it.split(",").let { Point(it[0].toInt(), it[1].toInt()) } }.windowed(2, 1)
            .map { Line(it[0], it[1]).toNormalForm() }
    }, implicitFloor)
}

@OptIn(ExperimentalTime::class)
fun main() {
    measureTime {
        val cave = buildCave(readResourceFile("_2022/day14_input.txt"))
        var sandDropped = 0
        while (cave.dropSand() != null) sandDropped++
        println("Sand drops before sand falls out of cave: " + sandDropped)
        cave.print()
    }.let { println("Part 1 finished in $it") }


    val cave2 = buildCave(readResourceFile("_2022/day14_input.txt"), implicitFloor = true)
    var sandDropped2 = 0
    var latest: Point?
    while (cave2.dropSand().also { sandDropped2++; latest = it } != Point(500, 0)) {
        sandDropped2++
        if (sandDropped2 % 100_000 == 0) {
            println("Total sand dropped = $sandDropped2. Occupied space = ${cave2.occupiedSpace.values.flatten().size} . Latest = $latest ")
            print(cave2)
        }
    }
    println("Sand drops before sand entry is blocked: " + sandDropped2)
    cave2.print()
}