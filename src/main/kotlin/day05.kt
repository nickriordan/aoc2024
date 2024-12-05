fun main() {
    data class PageOrder(val first: Int, val second: Int)
    data class PrintList(val pages: List<Int>) {
        fun isValid(orderRules: Set<PageOrder>) = (0..<pages.indices.last).all { firstIx ->
            pages.drop(firstIx + 1).all { second -> orderRules.contains(PageOrder(pages[firstIx], second)) }
        }

        fun reOrder(orderRules: Set<PageOrder>): PrintList {
            var p = pages
            (0..<p.indices.last).forEach { firstIx ->
                (firstIx + 1..p.indices.last).forEach { secondIx ->
                    if (orderRules.contains(PageOrder(p[secondIx], p[firstIx]))) {
                        p = p.take(firstIx) + p[secondIx] + p.subList(firstIx, secondIx) + p.drop(secondIx + 1)
                    }
                }
            }
            return PrintList(p)
        }

        fun middlePage() = pages[pages.size / 2]
    }

    fun loadPageRules(lines: Sequence<String>) =
        lines.filter { it.contains('|') }.map { it.split('|').map { it.toInt() } }.map { PageOrder(it[0], it[1]) }
            .toSet()

    fun loadPrintOrdering(lines: Sequence<String>) =
        lines.filter { it.contains(',') }.map { it.split(',').map { it.toInt() } }.map { PrintList(it) }.toList()

    fun part1(orderRules: Set<PageOrder>, printLists: List<PrintList>) =
        printLists.filter { it.isValid(orderRules) }.sumOf { it.middlePage() }

    fun part2(pageRules: Set<PageOrder>, printList: List<PrintList>) =
        printList.filter { !it.isValid(pageRules) }.sumOf { it.reOrder(pageRules).middlePage() }

    val data = loadFileAsLines("day05-data.txt")
    val pageRules = loadPageRules(data)
    val printOrdering = loadPrintOrdering(data)

    println(part1(pageRules, printOrdering))
    println(part2(pageRules, printOrdering))
}