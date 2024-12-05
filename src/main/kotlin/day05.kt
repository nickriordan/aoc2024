fun main() {

    fun loadPageRules(lines: Sequence<String>) =
        lines.filter { it.contains('|') }.map { it.split('|').map { it.toInt() } }.map { it[0] to it[1] }.toList()

    fun loadPrintOrdering(lines: Sequence<String>) =
        lines.filter { it.contains(',') }.map { it.split(',').map { it.toInt() } }.toList()

    fun part1(pageRules: List<Pair<Int, Int>>, printOrdering: List<List<Int>>) = printOrdering.sumOf { print ->
        fun validateOrder(input: List<Int>) = (0..<input.indices.last).all { firstIx ->
            (firstIx + 1..input.indices.last).all { secondIx -> pageRules.contains(input[firstIx] to input[secondIx]) }
        }

        if (validateOrder(print)) print[print.size / 2] else 0
    }

    fun part2(pageRules: List<Pair<Int, Int>>, printOrdering: List<List<Int>>): Int {
        fun reOrder(input: List<Int>): List<Int> {
            var text = input
            (0..<input.indices.last).forEach { firstIx ->
                (firstIx + 1..input.indices.last).forEach { secondIx ->
                    if (pageRules.contains(Pair(text[secondIx], text[firstIx]))) {
                        text = text.take(firstIx) + text[secondIx] + text.subList(
                            firstIx,
                            secondIx
                        ) + text.drop(secondIx + 1)
                    }
                }
            }
            return text
        }

        return printOrdering.map { it to reOrder(it) }.filter { it.first != it.second }.sumOf {
            val reOrdered = reOrder(it.second)
            reOrdered[reOrdered.size / 2]
        }
    }

    val data = loadFileAsLines("day05-data.txt")
    val pageRules = loadPageRules(data)
    val printOrdering = loadPrintOrdering(data)

    println(part1(pageRules, printOrdering))
    println(part2(pageRules, printOrdering))
}