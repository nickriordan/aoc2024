fun main() {
    class LabGrid(private val data: List<String>) : Grid(data[0].length, data.size) {

        inner class Guard(val location: Point, val direction: Char) {
            override fun toString() = "$direction $location"
            override fun equals(other: Any?) = other === this ||
                    (other is Guard && other.location == location && other.direction == direction)

            override fun hashCode() = location.hashCode() + direction.hashCode()
        }

        val guardInitialPosition =
            (0..<ySize).flatMap { y -> (0..<xSize).map { x -> Point(x, y).let { it to data[it.y][it.x] } } }
                .first { it.second in listOf('<', '>', '^', 'v') }.let { Guard(it.first, it.second) }

        fun getPath(at: (Point) -> Char) = generateSequence({ guardInitialPosition }) { guard ->
            // Took a while to figure out you could get the situation where you need to rotate more than once
            fun next(direction: Char): Guard? =
                when (direction) {
                    '^' -> guard.location.north()?.let { if (at(it) == '#') next('>') else Guard(it, direction) }
                    'v' -> guard.location.south()?.let { if (at(it) == '#') next('<') else Guard(it, direction) }
                    '>' -> guard.location.east()?.let { if (at(it) == '#') next('v') else Guard(it, direction) }
                    '<' -> guard.location.west()?.let { if (at(it) == '#') next('^') else Guard(it, direction) }
                    else -> throw Exception()
                }

            next(guard.direction)
        }

        fun originalPath() = getPath { pt: Point -> data[pt.y][pt.x] }.map { it.location }.toSet()

        fun isPathLoop(path: Sequence<Guard>): Boolean {
            val seen = mutableSetOf<Guard>()
            val iter = path.iterator()
            while (iter.hasNext())
                if (!seen.add(iter.next()))
                    return true

            return false
        }

        fun part1() = originalPath().size

        fun part2() = originalPath().count { location ->
            isPathLoop(getPath { pt: Point -> if (pt == location) '#' else data[pt.y][pt.x] })
        }
    }

    val data = loadFileAsLines("day06-data.txt")
    val grid = LabGrid(data.toList())
    println(grid.part1())
    println(grid.part2())
}