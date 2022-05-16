package com.djei.balltree

class BallTree(
    private val points: List<Point>,
    private val distance: (Point, Point) -> Double,
    private val maxDepth: Int
) {
    val root: BallTreeNode = BallTreeNode(points, distance, maxDepth)

    fun getKNearestPoints(point: Point, k: Int): List<Point> {
        return emptyList()
    }

    fun getPointsWithinRange(point: Point, range: Double): List<Point> {
        return emptyList()
    }
}