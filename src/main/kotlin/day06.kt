fun main() {
    class LabGrid(private val data: List<String>) : Grid(data[0].length, data.size) {

        inner class Guard(val location: Point, val direction: Char) {
            override fun toString() = "$direction $location"
        }

        val guardInitialPosition =
            (0..<ySize).flatMap { y -> (0..<xSize).map { x -> Point(x, y).let { it to data[it.y][it.x] } } }
                .first { it.second in listOf('<', '>', '^', 'v') }.let { Guard(it.first, it.second) }

        fun getPath(at: (Point) -> Char) = generateSequence({ guardInitialPosition }) { guard ->
            fun nextLocation(direction: Char): Guard? =
                when (direction) {
                    '^' -> guard.location.north()?.let { if (at(it) == '#') nextLocation('>') else Guard(it, direction) }
                    'v' -> guard.location.south()?.let { if (at(it) == '#') nextLocation('<') else Guard(it, direction) }
                    '>' -> guard.location.east()?.let { if (at(it) == '#') nextLocation('v') else Guard(it, direction) }
                    '<' -> guard.location.west()?.let { if (at(it) == '#') nextLocation('^') else Guard(it, direction) }
                    else -> throw Exception()
                }

            nextLocation(guard.direction)
        }

        fun part1() = getPath { pt: Point -> data[pt.y][pt.x] }.map { it.location }.toSet().size

        fun part2(): Int {
            val potentialLocations = (0..<ySize).flatMap { y ->
                (0..<xSize).mapNotNull { x -> if (data[y][x] == '.') Point(x, y) else null }
            }

            // TODO: we should look for the first two elements of the path
            // TODO: only need to look along the initial path for options
            val size = xSize * ySize
            return potentialLocations.filter { location ->
                val path = getPath { pt: Point -> if (pt == location) '#' else data[pt.y][pt.x] }
                path.take(size).count() == size
            }.size
        }
    }

    val data = loadFileAsLines("day06-data.txt")
    val grid = LabGrid(data.toList())
    println(grid.part1())
    println(grid.part2())
}