fun main() {
    data class PageOrder(val first: Int, val second: Int)
    data class PrintList(val pages: List<Int>) {
        fun isValid(pageOrderRules: Set<PageOrder>) = (0..<pages.indices.last).all { firstIx ->
            pages.drop(firstIx + 1).all { second -> pageOrderRules.contains(PageOrder(pages[firstIx], second)) }
        }

        fun reOrder(pageOrderRules: Set<PageOrder>) =
            PrintList((0..<pages.indices.last).fold(pages) { p, firstIx ->
                (firstIx + 1..p.indices.last).fold(p) { p, secondIx ->
                    if (pageOrderRules.contains(PageOrder(p[secondIx], p[firstIx]))) {
                        p.take(firstIx) + p[secondIx] + p.subList(firstIx, secondIx) + p.drop(secondIx + 1)
                    } else
                        pages
                }
            })

        fun middlePage() = pages[pages.size / 2]
    }

    fun loadPageOrderRules(inp: Sequence<String>) =
        inp.filter { it.contains('|') }.map { it.split('|').map { it.toInt() } }.map { PageOrder(it[0], it[1]) }.toSet()

    fun loadPrintLists(lines: Sequence<String>) =
        lines.filter { it.contains(',') }.map { it.split(',').map { it.toInt() } }.map { PrintList(it) }.toList()

    fun part1(orderRules: Set<PageOrder>, printLists: List<PrintList>) =
        printLists.filter { it.isValid(orderRules) }.sumOf { it.middlePage() }

    fun part2(pageOrderRules: Set<PageOrder>, printLists: List<PrintList>) =
        printLists.filter { !it.isValid(pageOrderRules) }.sumOf { it.reOrder(pageOrderRules).middlePage() }

    val data = loadFileAsLines("day05-data.txt")
    val pageOrderRules = loadPageOrderRules(data)
    val printLists = loadPrintLists(data)

    println(part1(pageOrderRules, printLists))
    println(part2(pageOrderRules, printLists))
}