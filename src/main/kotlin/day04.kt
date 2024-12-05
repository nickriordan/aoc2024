fun main() {
    class TextGrid(val lines: List<String>) : Grid(lines.first().length, lines.size) {
        private fun charAt(pt: Point) = if (pt.isValid()) lines[pt.y][pt.x] else null
        fun wordFrom(seq: Sequence<Point>, len: Int) = seq.take(len).map { charAt(it) }.joinToString("")

        fun wordsFromPoint(pt: Point, len: Int) = listOf(
            wordFrom(pt.northSequence(), len),
            wordFrom(pt.northEastSequence(), len),
            wordFrom(pt.eastSequence(), len),
            wordFrom(pt.southEastSequence(), len),
            wordFrom(pt.southSequence(), len),
            wordFrom(pt.southWestSequence(), len),
            wordFrom(pt.westSequence(), len),
            wordFrom(pt.northWestSequence(), len)
        ).filter { it.length == len }

        fun countWordsMatchingFromPoint(word: String) =
            allPoints().flatMap { wordsFromPoint(it, word.length) }.count { it == word }

        val xMasWords = listOf("MAS", "SAM")
        fun isXmasPoint(pt: Point) =
            xMasWords.contains(pt.northWest()?.southEastSequence()?.let { wordFrom(it, 3) }) &&
                    xMasWords.contains(pt.southWest()?.northEastSequence()?.let { wordFrom(it, 3) })

        fun countXMasPoints() = allPoints().count { isXmasPoint(it) }
    }


    val grid = TextGrid(loadFileAsLines("day04-data.txt").toList())
    fun part1() = grid.countWordsMatchingFromPoint("XMAS")
    fun part2() = grid.countXMasPoints()

    println(part1())
    println(part2())
}