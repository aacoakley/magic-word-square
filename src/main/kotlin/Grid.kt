class Grid(private val size: Int) {
    private val empty = '.'

    private val grid: Array<Array<Char>> = Array(size) { Array(size) { '.' } }

    fun set(string: String) {
        val start = grid[size - 1].indexOf(empty)
        val chars = string.toCharArray()
        for (i in start until size) {
            val char = chars[i]
            grid[i][start] = char
            grid[start][i] = char
        }
    }

    fun remove() {
        val start = grid[size - 1].indexOf(empty) - 1
        for (i in start until size) {
            try {
                grid[i][start] = empty
                grid[start][i] = empty
            }catch (e: IndexOutOfBoundsException) {
                print()
                println("oops")
            }
        }
    }

    fun get(i: Pair<Int, Int>) = grid[i.first - 1][i.second - 1]

    fun level(level: Int): String {
        val charArray = CharArray(level)
        for (i in 0 until level) {
            charArray[i] = grid[level][i]
        }
        return charArray.concatToString()
    }

    fun check() = grid.forEach { it.contains('.') }

    fun print() {
        val chars = mutableListOf<Char>()

        for (i in 0 until size) {
            for (j in 0 until size) {
                chars.add(grid[j][i])
                chars.add(' ')
                chars.add(' ')
            }
            chars.add('\n')
        }
        chars.forEach { print(it.uppercase()) }
    }
}