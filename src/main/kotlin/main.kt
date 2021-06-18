import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.system.exitProcess

val length = 10
val topLevelWords = mutableListOf<String>()
var runs = 0
val start: Instant = Instant.now()

suspend fun main(args: Array<String>) {
    val words = readFile("10/words_alpha.txt")
    words.addAll(readFile("10/corncob_lowercase.txt"))
    words.addAll(readFile("10/MASTER.txt"))
    words.addAll(readFile("10/wordlist.10000.txt"))
    words.addAll(readFile("10/wordlist.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match1.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match2.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match3.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match4.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match5.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match6.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match7.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match8.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match9.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match10.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match11.txt"))
    words.addAll(readFile("10/wlist_all/wlist_match12.txt"))
    run(words.sorted())
}

suspend fun run(words: List<String>) = coroutineScope {
    repeat(50) {
        launch {
            createSquare(words)
            return@launch
        }
    }
}

fun createSquare(words: List<String>) {
    val grid = Grid(length)
    val usedWords = mutableListOf<String>()

    for (it in words) {
        if (topLevelWords.contains(it)) continue

        usedWords.add(it)
        topLevelWords.add(it)
        grid.set(it)

        if (words.nextWord(usedWords, grid, 0)) {
            grid.print()
            println("${Instant.now().toEpochMilli() - start.toEpochMilli()} millis taken on $runs runs.")
            return
        } else {
            grid.remove()
            usedWords.minus(it)
        }
    }
}

fun List<String>.nextWord(usedWords: MutableList<String>, grid: Grid, level: Int): Boolean {

    val nextLevel = level + 1

    val eligibleWords = this.filter { it.substring(0, nextLevel) == grid.level(nextLevel) && !usedWords.contains(it) }
    eligibleWords.forEach {
        runs++
        grid.set(it)
        usedWords.add(it)

        if (nextLevel == length - 1 || nextWord(usedWords, grid, nextLevel)) {
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
        .filter { it.length == length }
        .toMutableSet()
}

object Read