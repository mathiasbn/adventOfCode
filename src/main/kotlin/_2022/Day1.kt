fun main(args:Array<String>){
    println("Q1 max calories: ${maxCalories(readResourceFile("day1_input.txt"))}")
    println("Q2 top 3 max calories: " + top3TotalCalories(readResourceFile("day1_input.txt")))
}

fun maxCalories(input: String): Int {
    return caloriesEach(input).max()
}

fun top3TotalCalories(input: String): Int {
    return caloriesEach(input).sortedDescending().take(3).sum()
}

private fun caloriesEach(input: String) = input.lines().fold(listOf<Int>()) { elves, cal ->
    if (cal.isBlank())
        elves.plus(0)
    else {
        val currentElvesCalories = elves.lastOrNull() ?: 0
        elves.dropLast(1).plus(currentElvesCalories + cal.toInt())
    }
}