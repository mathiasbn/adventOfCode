import util.Point
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.math.abs

data class SensorData(val sensor: Point, val beacon: Point) {
    fun isACloserBeacon(other: Point): Boolean = sensor taxiDistance other <= sensor taxiDistance beacon

    fun beaconExcludedPoint(): Stream<Point> {
        val maxDistance = sensor taxiDistance beacon
        println(maxDistance)
        return (sensor.x-maxDistance .. sensor.x + maxDistance).toList().parallelStream().flatMap {x->
            (sensor.y-maxDistance .. sensor.y + maxDistance).toList().parallelStream().map {y->
                Point(x,y)
            }
        }
            .filter { isACloserBeacon(it) }
    }
}

fun parseSensorData(input: String): List<SensorData> {
    return input.lines().filter { it.isNotBlank() }
        .map { Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)").matchEntire(it)!! }
        .map {
            it.groupValues.let {
                SensorData(
                    Point(it[1].toInt(), it[2].toInt()),
                    Point(it[3].toInt(), it[4].toInt())
                )
            }
        }
}

infix fun Point.taxiDistance(to: Point): Int = abs(x - to.x) + abs(y - to.y)

fun List<SensorData>.print(nonBeaconPointsInRow: Set<Point> = emptySet()) {
    val leftMost = flatMap { listOf(it.beacon, it.sensor) }.map { it.x }.min()
    val rightMost = flatMap { listOf(it.beacon, it.sensor) }.map { it.x }.max()
    val xRange = leftMost - 3..rightMost + 3
    val upMost = flatMap { listOf(it.beacon, it.sensor) }.map { it.y }.min()
    val downMost = flatMap { listOf(it.beacon, it.sensor) }.map { it.y }.max()
    val yRange = upMost..downMost
    print(xRange, yRange, nonBeaconPointsInRow)
}

private fun List<SensorData>.print(
    xRange: IntRange,
    yRange: IntRange,
    nonBeaconPointsInRow: Set<Point> = emptySet()
) {
    val sensors = map { it.sensor }.toSet()
    val beacons = map { it.beacon }.toSet()

    print("".padStart(3))
    (xRange.first - 3..xRange.last + 3).forEach { x ->
        if (x == 0) print("X")
        else print(abs(x % 10))
    }
    println()
    yRange.forEach { y ->
        print(y.toString().padStart(3))
        (xRange).forEach { x ->
            val point = Point(x, y)
            if (sensors.contains(point)) print("S")
            else if (beacons.contains(point)) print("B")
            else if (nonBeaconPointsInRow.contains(point)) print("#")
            else print(".")
        }
        println()
    }
}

fun List<SensorData>.countNotViableBeaconPositions(y: Int) =
    nonBeaconPointsInRow(y).also { /*print(it)*/ }.count()

private fun List<SensorData>.nonBeaconPointsInRow(y: Int): Set<Point> {
    val leftMostSensor = map { it.sensor.x }.min() - 900//00000
    val rightMostSensor = map { it.sensor.x }.max() + 900//00000
    return beaconPointsInRange(leftMostSensor..rightMostSensor, y..y)
        .filter { point -> !any { it.beacon == point } }.toSet()

}

private fun List<SensorData>.beaconPointsInRange(
    xRange: IntRange,
    yRange: IntRange,
    closerPoints: Boolean = true
): Set<Point> {
    return xRange.toList().parallelStream().flatMap { x -> yRange.toList().parallelStream().map { y -> Point(x, y) } }
        .filter { point ->
            if(point.x%50000==0) println("Testing: $point")
            if(closerPoints)any { it.isACloserBeacon(point) } else none { it.isACloserBeacon(point) }
        }
        .collect(Collectors.toSet())
}

fun List<SensorData>.findPossibleBeaconPointIn(xRange: IntRange, yRange: IntRange): Point {
    val hiddenBeacons = beaconPointsInRange(xRange, yRange, closerPoints = false)
        .filter { point -> !any { it.beacon == point } }

//    return xRange.asSequence().flatMap { x ->
//        yRange.asSequence().filter { y -> !nonPoints.contains(Point(x, y)) }
//            .map { y -> Point(x, y) }
//    }.also { println(it.toList()) }.single()

    return hiddenBeacons.single()
}

fun Point.tuningFrequency(): Int = x * 4000000 + y

fun main() {
//    println(
//        "non beacon points in row 2000000: " + parseSensorData(readResourceFile("day15_input.txt"))
//            .countNotViableBeaconPositions(2000000)
//    )

    val sensorData = parseSensorData(readResourceFile("day15_input.txt"))
    val excludedPoints = sensorData.parallelStream().flatMap { it.beaconExcludedPoint() }.collect(Collectors.toSet())
    println("Excluded points: ${excludedPoints.size}")
//    val beaconPointIn = sensorData.findPossibleBeaconPointIn(0..4000000, 0..4000000)
    val beaconPointIn =  (0..4000000).asSequence().flatMap { x ->
        (0..4000000).asSequence().filter { y -> !excludedPoints.contains(Point(x, y)) }
            .map { y -> Point(x, y) }
    }.single()

    println("Tuning frequency of hidden beacon: " + beaconPointIn.tuningFrequency()
    )
}
