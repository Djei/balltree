package com.djei.balltree

class BallTreeNode(
    val points: List<Point>,
    private val distance: (Point, Point) -> Double,
    private val maxDepth: Int
) {
    val centroid: Point
    val radius: Double
    lateinit var childs: Pair<BallTreeNode?, BallTreeNode?>
    private val apex: Point

    init {
        if (points.isEmpty()) {
            throw IllegalArgumentException("points should not be empty")
        }
        centroid = calculateCentroid()
        apex = calculateApex()
        radius = distance(centroid, apex)
    }

    fun split(): Pair<BallTreeNode?, BallTreeNode?> {
        if (maxDepth == 0 || points.count() == 1) {
            return Pair(null, null)
        }
        val partition1Points = mutableListOf<Point>()
        val partition2Points = mutableListOf<Point>()
        // First partition uses apex as center, second partition uses the point the furthest away from apex as center
        val firstPartitionCenter = apex
        val secondPartitionCenter = points.maxByOrNull { distance(apex, it) }!!
        points.forEach {
            val distanceFromFirstPartitionCenter = distance(firstPartitionCenter, it)
            val distanceFromSecondPartitionCenter = distance(secondPartitionCenter, it)
            if (distanceFromFirstPartitionCenter < distanceFromSecondPartitionCenter) {
                partition1Points.add(it)
            } else {
                partition2Points.add(it)
            }
        }
        val leftChild = if (partition1Points.isEmpty()) {
            null
        } else {
            BallTreeNode(partition1Points, distance, maxDepth - 1)
        }
        val rightChild = if (partition2Points.isEmpty()) {
            null
        } else {
            BallTreeNode(partition2Points, distance, maxDepth - 1)
        }
        return Pair(leftChild, rightChild)
    }

    private fun calculateCentroid(): Point {
        val centroidCoordinates = DoubleArray(points[0].coordinates.size) { 0.0 }
        points.forEach { point ->
            point.coordinates.forEachIndexed { index, coordinate ->
                centroidCoordinates[index] += coordinate
            }
        }
        return Point(centroidCoordinates.map { it / points.size }.toDoubleArray())
    }

    private fun calculateApex(): Point {
        return points.maxByOrNull { distance(centroid, it) }!!
    }
}

data class Point(val coordinates: DoubleArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (!coordinates.contentEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        return coordinates.contentHashCode()
    }
}