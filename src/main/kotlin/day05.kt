fun main() {

    fun loadPageRules(lines: Sequence<String>) =
        lines.filter { it.contains('|') }.map { it.split('|').map { it.toInt() } }.map { it[0] to it[1] }.toList()

    fun loadPrintOrdering(lines: Sequence<String>) =
        lines.filter { it.contains(',') }.map { it.split(',').map { it.toInt() } }.toList()

    fun validateOrder(pageRules: List<Pair<Int, Int>>, pageList: List<Int>): Boolean {
        fun check(first: Int, second: Int) = !pageRules.contains(Pair(second, first))

        return (0..pageList.indices.last - 1).all { firstIx ->
            (firstIx + 1..pageList.indices.last).all { secondIx -> check(pageList[firstIx], pageList[secondIx]) }
        }
    }

    fun part1(pageRules: List<Pair<Int, Int>>, printOrdering: List<List<Int>>) = printOrdering.sumOf { print ->
        if (validateOrder(pageRules, print)) print[print.size / 2] else 0
    }

    val data = loadFileAsLines("day05-data.txt")
    val pageRules = loadPageRules(data)
    val printOrdering = loadPrintOrdering(data)

    println(part1(pageRules, printOrdering))
}