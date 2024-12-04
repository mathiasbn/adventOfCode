package util

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "($x,$y)"
    }

    fun step(direction: Direction) = when (direction) {
        Direction.U -> Pair(x, y - 1)
        Direction.UR -> Pair(x + 1, y - 1)
        Direction.R -> Pair(x + 1, y)
        Direction.DR -> Pair(x + 1, y + 1)
        Direction.D -> Pair(x, y + 1)
        Direction.DL -> Pair(x - 1, y + 1)
        Direction.L -> Pair(x - 1, y)
        Direction.UL -> Pair(x - 1, y - 1)
    }.let { if(it.first<0 || it.second<0) null else Point(it.first, it.second) }
}

enum class Direction { U, UR, R, DR, D, DL, L, UL }
data class GridPoint(val point: Point, val value: String)
class Grid(val rows: List<String>) {
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
}