data class Surrounding<T>(val up: T, val down: T, val left: T, val right: T)

fun parseForest(input: String): List<List<Int>> =
    input.lines().filter { it.isNotBlank() }
        .map { it.map { it.toString().toInt() }.toList() }

fun isTreeVisible(point: Point, forest: List<List<Int>>): Boolean {
    val treeVisibleInRow = isTreeVisibleInLine(point.x, forest[point.y])
    val treeVisibleInCol = isTreeVisibleInLine(point.y, forest.map { it[point.x] })
    return treeVisibleInCol || treeVisibleInRow
}

fun isTreeVisibleInLine(index: Int, row: List<Int>): Boolean {
    return index == 0 || index == row.size - 1 ||
            row[index] > row.filterIndexed { i, _ -> i > index }.max() ||
            row[index] > row.filterIndexed { i, _ -> i < index }.max()
}

fun visibleTreesFromTree(point: Point, forest: List<List<Int>>): Surrounding<Int> {
    val (left, right) = visibleTreesBeforeAfter(point.x, forest[point.y])
    val (up, down) = visibleTreesBeforeAfter(point.y, forest.map { it[point.x] })
    return Surrounding(up, down, left, right)
}

fun visibleTreesBeforeAfter(index: Int, line: List<Int>): Pair<Int, Int> {
    val indexValue = line[index]
    return Pair(
        line.filterIndexed { i, _ -> i < index }.reversed().takeUntil { it >= indexValue }.size,
        line.filterIndexed { i, _ -> i > index }.takeUntil { it >= indexValue }.size
    )
}

public inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (predicate(item))
            break
    }
    return list
}

fun iterateForest(forest: List<List<Int>>): Sequence<Point> = sequence {
    forest.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, col ->
            yield(Point(colIndex, rowIndex))
        }
    }
}

fun countVisibleTrees(input: String): Int {
    val forest = parseForest(input)
    return iterateForest(forest).count { isTreeVisible(it, forest) }
}

fun bestScenicView(input: String): Int {
    val forest = parseForest(input)
    return iterateForest(forest).maxOf {
        visibleTreesFromTree(it, forest).let {
            it.up * it.down * it.left * it.right
        }
    }
}

fun main() {
    println("Visible trees in forest: " + countVisibleTrees(readResourceFile("day8_input.txt")))
    println("Best scenic score in forest: " + bestScenicView(readResourceFile("day8_input.txt")))
}