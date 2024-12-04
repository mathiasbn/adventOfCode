import kotlin.streams.toList

private fun List<List<Int>>.heightAt(x: Int, y: Int) = this.getOrNull(y)?.getOrNull(x)
    ?.let { if (it.toChar() == 'S')
        'a'.code
    else if (it.toChar() == 'E')
        'z'.code
    else it }

data class Node(val x: Int, val y: Int)
data class Graph(
    val vertices: Set<Node>,
    val edges: Map<Node, Set<Node>>,
    val start: Node,
    val end: Node,
)

fun parseNodes(input: String): Triple<List<List<Int>>, Node, Node> {
    var start: Node? = null
    var end: Node? = null
    val nodes = input.lines().mapIndexed { y, row ->
        row.chars().toList().mapIndexed { x, height ->
            if (height.toChar() == 'S')
                start = Node(x, y)
            if (height.toChar() == 'E')
                end = Node(x, y)
            height
        }
    }
    return Triple(nodes, start!!, end!!)
}

fun edgesOf(node: Node, graph: List<List<Int>>): Set<Node> {
    val (x, y) = node
    val minHeight = graph.heightAt(x, y)!! - 1
    return setOfNotNull(
        if ((graph.heightAt(x - 1, y) ?: Int.MIN_VALUE) >= minHeight) Node(x - 1, y) else null,
        if ((graph.heightAt(x + 1, y) ?: Int.MIN_VALUE) >= minHeight) Node(x + 1, y) else null,
        if ((graph.heightAt(x, y - 1) ?: Int.MIN_VALUE) >= minHeight) Node(x, y - 1) else null,
        if ((graph.heightAt(x, y + 1) ?: Int.MIN_VALUE) >= minHeight) Node(x, y + 1) else null,
    )
}

fun Triple<List<List<Int>>, Node, Node>.createGraph(): Graph {
    val nodes = first.flatMapIndexed { y, row -> List(row.size) { x -> Node(x, y) } }.toSet()
    val edges = nodes.associateWith { edgesOf(it, first) }
    return Graph(nodes, edges, second, third)
}

fun dijkstra(graph: Graph): Map<Node,Node?> {
    val S: MutableSet<Node> = mutableSetOf() // a subset of vertices, for which we know the true distance

    val delta = graph.vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
    delta[graph.end] = 0

    val previous: MutableMap<Node, Node?> = graph.vertices.associateWith { null }.toMutableMap()

    while (S != graph.vertices) {
        val v: Node = delta
            .filter { !S.contains(it.key) }
            .minBy { it.value }
            .key

        graph.edges.getValue(v).minus(S).forEach { neighbor ->
            val newPath = delta.getValue(v) + 1

            if (newPath < delta.getValue(neighbor)) {
                delta[neighbor] = newPath
                previous[neighbor] = v
            }
        }

        S.add(v)
    }
    return previous.toMap()
}

fun shortestPath(shortestPathTree: Map<Node, Node?>, start: Node, end: Node): List<Node> {
    fun pathTo(start: Node, end: Node): List<Node> {
        if (shortestPathTree[end] == null) return listOf(end)
        return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
    }

    return pathTo(start, end)
}

fun main() {
    val rawNodes = parseNodes(readResourceFile("day12_input.txt"))
    val graph = rawNodes.createGraph()
    val map = dijkstra(graph)
    val shortestPath = shortestPath(map, graph.end, graph.start)
    val minus = shortestPath.minus(graph.start)
    println("Path to highest point: " + minus.size)

    val startNodes = rawNodes.first.flatMapIndexed{y,row -> row.mapIndexed { x, height -> if(height.toChar()=='a') Node(x,y) else null }}
        .filterNotNull()
    val shortestPaths = startNodes.map { shortestPath(map, graph.end, it) }
    val shortestPathFromALowestPoint = shortestPaths.minOf { it.minus(graph.end).size }
    println("Shortest path from a lowest point to the specific highest point: " + shortestPathFromALowestPoint)

}