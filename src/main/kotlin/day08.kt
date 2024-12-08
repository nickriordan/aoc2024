fun main() {

    class CityMap(private val data: List<String>) : Grid(data[0].length, data.size) {
        private fun antennaAt(pt: Point) = data[pt.y][pt.x]

        private fun freqToAntennas() = allPoints()
            .map { pt -> antennaAt(pt) to pt }
            .filter { it.first !in ".#" }
            .groupBy({ (ch, _) -> ch }, { (_, pt) -> pt })

        private fun antennaLocationPairs() = freqToAntennas().flatMap { (_, locations) -> locations.allPermutations() }

        private fun findCountAntiNodes(antiNodesOnPath: (Point, Point) -> List<Point>) = antennaLocationPairs()
            .flatMap { (p1, p2) -> antiNodesOnPath(p1, p2) }
            .toSet()
            .count()

        fun part1() = findCountAntiNodes { pt1, pt2 ->
            val offset = pt1 - pt2
            listOf(pt1 + offset, pt2 - offset).filter { it.isValid() }
        }

        fun part2() = findCountAntiNodes { pt1, pt2 ->
            val offset = pt1 - pt2
            listOf(pt1, pt2) +
                    generateSequence({ (pt1 + offset).validOrNull() }) { (it + offset).validOrNull() }.toList() +
                    generateSequence({ (pt2 - offset).validOrNull() }) { (it - offset).validOrNull() }.toList()
        }
    }

    val map = CityMap(loadFileAsLines("day08-data.txt").toList())

    println(map.part1())
    println(map.part2())
}