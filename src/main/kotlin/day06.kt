fun main() {
    val directions = listOf('<', '>', '^', 'v')

    data class Guard(val location: Grid.Point, val direction: Char)

    class LabGrid(private val data: List<String>) : Grid(data[0].length, data.size) {

        val guardInitial = allPoints().first { data[it.y][it.x] in directions }.let { Guard(it, data[it.y][it.x]) }

        fun pathSequence(at: (Point) -> Char) = generateSequence({ guardInitial }) { guard ->
            fun move(direction: Char): Guard? = when (direction) {
                '^' -> guard.location.north()?.let { if (at(it) == '#') move('>') else Guard(it, direction) }
                'v' -> guard.location.south()?.let { if (at(it) == '#') move('<') else Guard(it, direction) }
                '>' -> guard.location.east()?.let { if (at(it) == '#') move('v') else Guard(it, direction) }
                '<' -> guard.location.west()?.let { if (at(it) == '#') move('^') else Guard(it, direction) }
                else -> throw Exception()
            }
            move(guard.direction)
        }

        fun getPath() = pathSequence { pt: Point -> data[pt.y][pt.x] }.map { it.location }.toSet()

        fun isPathLoop(path: Sequence<Guard>) = mutableSetOf<Guard>().let { seen -> path.any { !seen.add(it) } }

        fun part1() = getPath().size

        fun part2() = getPath().count { loc -> isPathLoop(pathSequence { if (it == loc) '#' else data[it.y][it.x] }) }
    }

    val grid = LabGrid(loadFileAsLines("day06-data.txt").toList())
    println(grid.part1())
    println(grid.part2())
}