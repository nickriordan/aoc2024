fun main() {

    class CityMap(private val data: List<String>) : Grid(data[0].length, data.size) {
        private fun antennaAt(pt: Point) = data[pt.y][pt.x]

        fun antennaLocations() = allPoints().map { antennaAt(it) to it }.filter { it.first !in ".#" }
            .groupBy({ (ch, _) -> ch }, { (_, pt) -> pt })

        fun antiNodesFor(pt1: Point, pt2: Point) = listOf(pt1 + pt1 - pt2, pt2 + pt2 - pt1).filter { it.isValid() }

        fun allPairs(points: List<Point>) = points.indices.flatMap { ix1 ->
            (ix1 + 1..points.lastIndex).map { ix2 -> points[ix1] to points[ix2] }
        }

        fun part1() = antennaLocations().flatMap { (_, locations) ->
            allPairs(locations).flatMap { (p1, p2) -> antiNodesFor(p1, p2) }
        }.toSet().count()

    }

    val map = CityMap(loadFileAsLines("day08-data.txt").toList())

    println(map.part1())
}