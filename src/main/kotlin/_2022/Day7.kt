import java.util.*

sealed interface File {
    data class PlainFile(val name: String, val size: Int) : File
    data class Dir(val name: String) : File
}

sealed interface Command {
    data class Ls(val files: List<File>) : Command
    data class Cd(val dirName:String) : Command
}

class Terminal {

    val dirsSize = hashMapOf<String,Long>()
    val currentPath = Stack<String>()

    fun typeCommands(input: String) {
        var rest = input.lines().filter { it.isNotBlank() }
        while (rest.isNotEmpty()) {
            val (command, r ) = parseNextCommand(rest)
            handle(command)
            rest = r
        }
    }

    fun parseNextCommand(commands: List<String>): Pair<Command, List<String>> {
        return if (commands.first().startsWith("$ ls")) {
            val inputAfterCommand = commands.drop(1)
            val files = inputAfterCommand.takeWhile { !it.startsWith("$") }
            val command = Command.Ls(files
                .map {
                    if (it.startsWith("dir"))
                        File.Dir(it.split(" ")[1])
                    else
                        Regex("(\\d+) ([a-zA-Z0-9.]+)")
                            .matchEntire(it)!!
                            .groupValues.let { File.PlainFile(it[2], it[1].toInt()) }
                })
            Pair(command, inputAfterCommand.drop(files.size))
        } else {
            val command = Command.Cd(commands.first().replace("$ cd ", ""))
            Pair(command, commands.drop(1))
        }
    }

    private fun handle(command: Command) {
        when(command){
            is Command.Cd -> when(command.dirName) {
                "/" -> currentPath.also { it.clear() }.also { it.push("/") }
                ".." -> currentPath.also { it.pop() }
                else -> currentPath.push(command.dirName)
            }
            is Command.Ls -> {
                val path = currentPath.toList()
                val dirSize = command.files.filterIsInstance<File.PlainFile>().sumOf { it.size }
                addSizeToParantDirs(path, dirSize)
            }
        }
    }

    private fun addSizeToParantDirs(path: List<String>, dirSize: Int) {
        if (path.isNotEmpty()) {
            val pathStr = path.joinToString("/").replace("//","/")//drop root //
            dirsSize[pathStr] = (dirsSize[pathStr] ?: 0) + dirSize
            addSizeToParantDirs(path.dropLast(1),dirSize)
        }
    }

    fun sumOfDirs(maxSize: Int): Long = dirsSize.values.filter { it<=maxSize }.sum()
    fun dirToDelete(freeSpace: Int): Long {
        val spaceNeeded = 30_000_000 - (70_000_000 - dirsSize["/"]!!)
        return dirsSize.values.filter { it >= spaceNeeded }.min()
    }
}

fun main() {
    val terminal = Terminal()
    terminal.typeCommands(readResourceFile("day7_input.txt"))
    println("Sum of dir sizes less than 100k: " + terminal.sumOfDirs(100000))
    println("Size of dir to delete, in order to have 30M free space " + terminal.dirToDelete(30000000))
}