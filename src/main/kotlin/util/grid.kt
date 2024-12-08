package util

data class DirectedPoint(val point: Point, val direction: Direction) {
    fun step() = DirectedPoint(point.step(direction), direction)

    fun turnRight() =
        copy(direction = direction.turn90Right())

}

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "($x,$y)"
    }

    fun step(direction: Direction) = when (direction) {
        Direction.U -> Point(x, y - 1)
        Direction.UR -> Point(x + 1, y - 1)
        Direction.R -> Point(x + 1, y)
        Direction.DR -> Point(x + 1, y + 1)
        Direction.D -> Point(x, y + 1)
        Direction.DL -> Point(x - 1, y + 1)
        Direction.L -> Point(x - 1, y)
        Direction.UL -> Point(x - 1, y - 1)
    }

    infix fun undirectedLineTo(point: Point) =
        listOf(this, point)
            .sortedWith(compareBy<Point> { it.x }.thenBy { it.y })
            .let { (from, to) -> UndirectedLine(from,to) }

    fun reverseVector() = Point(-x,-y)

    fun step(direction: Point) = Point(x+direction.x,y+direction.y)
}

data class Line(val from: Point, val to: Point) : Iterable<Point> {
    val horizontal get() = from.x != to.x

    fun toNormalForm() =
        if (horizontal) Line(Point(Integer.min(from.x, to.x), from.y), Point(Integer.max(from.x, to.x), from.y))
        else Line(Point(from.x, Integer.min(from.y, to.y)), Point(from.x, Integer.max(from.y, to.y)))

    override fun iterator(): Iterator<Point> =
        (if (horizontal) (from.x..to.x).map { Point(it, from.y) }
        else (from.y..to.y).map { Point(from.x, it) }).iterator()
}

class UndirectedLine(val point1: Point, val point2: Point) {
    override fun equals(other: Any?): Boolean {
        if (other !is UndirectedLine) return false
        return setOf(point1,point2).equals(setOf(other.point1, other.point2))
    }

    override fun hashCode(): Int {
        return setOf(point1,point2).hashCode()
    }

    override fun toString(): String {
        return "$point1→$point2"
    }

    fun vector(): Point {
        return Point(point2.x-point1.x, point2.y-point1.y)
    }
}

enum class Direction {
    U, UR, R, DR, D, DL, L, UL;

    fun turn90Right() = when (this) {
        U -> R
        UR -> DR
        R -> D
        DR -> DL
        D -> L
        DL -> UL
        L -> U
        UL -> UR
    }

    fun presentation() = when (this) {
        U -> "↑"
        R -> "→"
        D -> "↓"
        L -> "←"
        else -> throw IllegalStateException()
    }

}

data class GridPoint(val point: Point, val value: String)
class Grid(rows: List<String>) {
    val rows = rows.map { row -> row.map { it.toString() }.toMutableList() }.toMutableList()
    fun rowIterator() = sequence {
        rows.forEachIndexed { i, row ->
            row.forEachIndexed { j, cell ->
                yield(
                    GridPoint(Point(j, i), cell.toString())
                )
            }
        }
    }

    operator fun get(point: Point): String? = rows.getOrNull(point.y)?.getOrNull(point.x)?.toString()
    operator fun set(point: Point, value: String) {
        rows[point.y][point.x] = value
    }

    fun print() {
        rows.forEach { row -> println(row.joinToString(separator = "")) }
    }

    operator fun contains(point: Point) = rows.getOrNull(point.y)?.getOrNull(point.x) != null
}