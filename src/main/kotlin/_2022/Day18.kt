import Plane.*
import util.Point

data class Cube(val point: Point3d) {
    val planes: Set<Plane> = setOf(
        XPlane(point), XPlane(point.copy(x = point.x + 1)),
        YPlane(point), YPlane(point.copy(y = point.y + 1)),
        ZPlane(point), ZPlane(point.copy(z = point.z + 1))

    )
}

data class Point3d(val x: Int, val y: Int, val z: Int)

sealed interface Plane {
    val point: Point3d

    data class XPlane(override val point: Point3d) : Plane
    data class YPlane(override val point: Point3d) : Plane
    data class ZPlane(override val point: Point3d) : Plane
}


fun parseCubes(input: String): List<Cube> {
    return input.lines().filter { it.isNotBlank() }
        .map { it.split(",").let { (x, y, z) -> Cube(Point3d(x.toInt(), y.toInt(), z.toInt())) } }
}

infix fun <T> Set<T>.symmetricDifference(other: Set<T>): Set<T> {
    val mine = this subtract other
    val theirs = other subtract this
    return mine union theirs
}

inline fun <reified T : Plane> List<Cube>.planesOfType() = asSequence().flatMap { it.planes }
    .filterIsInstance<T>().map { it.point }
    .map { (x, y, _) -> Point(x, y) }.toSet()

fun List<Cube>.surfaceArea() = map { it.planes }
    .reduce(Set<Plane>::symmetricDifference)

fun draw(cubes: List<Cube>) {
    val zPlane = cubes.planesOfType<ZPlane>()
    val yPlane = cubes.planesOfType<YPlane>()
    val xPlane = cubes.planesOfType<XPlane>()

    print("XY place")
    repeat(30) { print(" ") }
    print("XZ place")
    repeat(30) { print(" ") }
    print("YZ place")

    (1..zPlane.maxOf { it.y }).forEach { y ->
        println()
        (1..zPlane.maxOf { it.x }).forEach { x ->
            if (zPlane.contains(Point(x, y))) print("O")
            else print(" ")
        }
        repeat(20) { print(" ") }
        (1..yPlane.maxOf { it.x }).forEach { x ->
            if (yPlane.contains(Point(x, y))) print("O")
            else print(" ")
        }
        repeat(20) { print(" ") }
        (1..xPlane.maxOf { it.x }).forEach { x ->
            if (xPlane.contains(Point(x, y))) print("O")
            else print(" ")
        }
    }
}

fun Set<Plane>.onlyExterior(): Set<Plane> {
    return asSequence().filter {plane->
        when (plane) {
            is XPlane -> {
                val (min, max) = this.filterIsInstance<XPlane>()
                    .filter { it.point.y == plane.point.y && it.point.z == plane.point.z }
                    .map { it.point.x }.sorted().let { Pair(it.first(), it.last()) }
                plane.point.x !in (min + 1) until max
            }
            is YPlane -> {
                val (min, max) = this.filterIsInstance<YPlane>()
                    .filter { it.point.x == plane.point.x && it.point.z == plane.point.z }
                    .map { it.point.y }.sorted().let { Pair(it.first(), it.last()) }
                plane.point.y !in (min + 1) until max
            }
            is ZPlane -> {
                val (min, max) = this.filterIsInstance<ZPlane>()
                    .filter { it.point.x == plane.point.x && it.point.y == plane.point.y }
                    .map { it.point.z }.sorted().let { Pair(it.first(), it.last()) }
                plane.point.z !in (min + 1) until max
            }
        }
    }.toSet()
}

fun main() {
    val cubes = parseCubes(readResourceFile("day18_input.txt"))
    println("Non covered lave cube sides: " + cubes.surfaceArea().size)
    draw(cubes)
}
