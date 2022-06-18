package com.djei.balltree

import java.util.*

class BallTree(
    points: List<Point>,
    private val distance: (Point, Point) -> Double,
    maxDepth: Int
) {
    val root: BallTreeNode = BallTreeNode(points, distance, maxDepth)

    init {
        val queue = LinkedList<BallTreeNode>()
        queue.push(root)
        while (queue.isNotEmpty()) {
            val node = queue.pop()
            val childs = node.split()
            node.childs = childs
            if (childs.first != null) {
                queue.push(childs.first)
            }
            if (childs.second != null) {
                queue.push(childs.second)
            }
        }
    }

    fun getKNearestNeighbours(target: Point, k: Int): PriorityQueue<Point> {
        val result = PriorityQueue(compareBy<Point> { distance(target, it) }.reversed())
        computeKNearestNeighbours(target, k, result, root)
        return result
    }

    private fun computeKNearestNeighbours(
        target: Point,
        k: Int,
        neighbours: PriorityQueue<Point>,
        ballTreeNode: BallTreeNode
    ): PriorityQueue<Point> {
        if (skipBallTreeNode(target, k, neighbours, ballTreeNode)) {
            return neighbours
        } else if (ballTreeNode.childs.first == null && ballTreeNode.childs.second == null) {
            ballTreeNode.points.forEach {
                if (neighbours.count() < k) {
                    // neighbours collection has not yet reached threshold k, always add points
                    neighbours.add(it)
                } else if (distance(target, it) < distance(target, neighbours.peek())) {
                    // neighbours collection has reached threshold k, only add point if it is closer than the furthest
                    neighbours.add(it)
                    // then remove point that is the furthest away
                    neighbours.poll()
                }
            }
        } else {
            val child1 = ballTreeNode.childs.first
            val child2 = ballTreeNode.childs.second
            if (child1 != null && child2 == null) {
                computeKNearestNeighbours(target, k, neighbours, child1)
            } else if (child1 == null && child2 != null) {
                computeKNearestNeighbours(target, k, neighbours, child2)
            } else if (child1 != null && child2 != null) {
                listOf(child1, child2)
                    .sortedBy { distance(target, it.centroid) }
                    .forEach {
                        computeKNearestNeighbours(target, k, neighbours, it)
                    }
            }
        }
        return neighbours
    }

    private fun skipBallTreeNode(
        target: Point,
        k: Int,
        neighbours: PriorityQueue<Point>,
        ballTreeNode: BallTreeNode
    ): Boolean {
        return if (neighbours.count() == k) {
            val currentFurthestNeighbour = neighbours.peek()
            // ball tree node can be skipped if all its points are further than our current furthest neighbour
            // this is where search speed can be gained, skipping nodes and their content from needing to be searched
            distance.invoke(target, ballTreeNode.centroid) - ballTreeNode.radius >=
                    distance.invoke(target, currentFurthestNeighbour)
        } else {
            // never skip ball tree node if neighbours collection has not yet reached threshold k
            false
        }
    }


    fun getPointsWithinRange(point: Point, range: Double): List<Point> {
        return emptyList()
    }
}