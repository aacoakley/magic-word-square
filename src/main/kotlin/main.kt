import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.system.exitProcess

val length = 7
val usedWords = mutableListOf<String>()
var runs = 0
val start: Instant = Instant.now()

suspend fun main(args: Array<String>) {

//    val set = readFile("10/words_alpha.txt")
//    set.addAll(readFile("10/corncob_lowercase.txt"))
//    set.addAll(readFile("10/MASTER.txt"))
//    set.addAll(readFile("10/wordlist.10000.txt"))
//    set.addAll(readFile("10/wordlist.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match1.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match2.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match3.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match4.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match5.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match6.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match7.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match8.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match9.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match10.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match11.txt"))
//    set.addAll(readFile("10/wlist_all/wlist_match12.txt"))


//    val words = listOf("heart",
//        "emnet",
//        "ember",
//        "opera",
//        "arepo",
//        "abuse",
//        "rotas",
//        "resin",
//        "trend")

    run()

}

suspend fun run() = coroutineScope {
    val process = launch {
        val grid = Grid(length)
        val words = readFile("corncob.txt", length).sorted()

        words.forEach {
            usedWords.add(it)
            usedWords.add("annuli")
            usedWords.add("begone")
            usedWords.add("botany")
            usedWords.add("seared")
            usedWords.add("condor")
//        val grids = mutableListOf<Grid>()
            grid.set(it)
            if (words.nextWord(grid, 0)) {
                grid.print()
                println("${Instant.now().toEpochMilli() - start.toEpochMilli()} millis taken on $runs runs.")
                exitProcess(0)
            } else {
                grid.remove()
                usedWords.minus(it)
            }
        }

        println("Unable to make a magic square with a size of $length.")
    }
    val status = launch {
        while (true) {
            println("Number of runs: $runs")
            delay(1_000)
        }
    }

}

fun List<String>.nextWord(grid: Grid, level: Int): Boolean {

    val nextLevel = level + 1

    val eligibleWords = this.filter { it.substring(0, nextLevel) == grid.level(nextLevel) && !usedWords.contains(it) }
    eligibleWords.forEach {
        runs++
//        println(runs)
        grid.set(it)
        usedWords.add(it)

        if (nextLevel == length - 1 || nextWord(grid, nextLevel)) {
            return true
        } else if (nextLevel == 0) {
            exitProcess(0)
        }
        grid.remove()
        usedWords.remove(it)
    }
    return false
}

fun readFile(fileName: String, length: Int = 10): MutableSet<String> {
    return Read.javaClass.getResource(fileName)!!.readText()
        .lowercase()
        .lines()
        .filter { it.length == length  }
        .toMutableSet()
}

object Read


fun testGrid(): Grid {
    val grid = Grid(5)
    grid.set("heart")
    grid.set("ember")
    grid.set("abuse")
    grid.set("resin")
    grid.set("trend")
    return grid
}