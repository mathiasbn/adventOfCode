data class Point(val x: Int, val y: Int){
    override fun toString(): String {
        return "($x,$y)"
    }
}

fun readResourceFile(s: String) = object {}.javaClass.getResourceAsStream(s).bufferedReader().readText()