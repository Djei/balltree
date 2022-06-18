package com.djei.balltree

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BallTreeCorrectnessTest : BallTreeAbstractTest() {
    @Test
    fun ballTreeSmallConstructionTest() {
        val points = listOf(
            Point(doubleArrayOf(0.0, 0.0)),
            Point(doubleArrayOf(1.0, 1.0)),
            Point(doubleArrayOf(0.5, 0.5)),
            Point(doubleArrayOf(-0.5, -0.5)),
            Point(doubleArrayOf(-1.0, -1.0))
        )
        val ballTree = BallTree(points, this::euclidianDistance, 50)

        Assertions.assertThat(ballTree.root.centroid.coordinates[0]).isEqualTo(0.0)
        Assertions.assertThat(ballTree.root.centroid.coordinates[1]).isEqualTo(0.0)
    }

    @Test
    fun ballTreeKNearestNeighboursTest() {
        val points = listOf(
            Point(doubleArrayOf(0.0, 0.0)),
            Point(doubleArrayOf(1.0, 1.0)),
            Point(doubleArrayOf(0.5, 0.5)),
            Point(doubleArrayOf(-0.5, -0.5)),
            Point(doubleArrayOf(-1.0, -1.0))
        )
        val ballTree = BallTree(points, this::euclidianDistance, 50)
        val neighbours = ballTree.getKNearestNeighbours(Point(doubleArrayOf(0.15, 0.15)), 2)

        Assertions.assertThat(neighbours).hasSize(2)
        Assertions.assertThat(neighbours.poll()).isEqualTo(Point(doubleArrayOf(0.5, 0.5)))
        Assertions.assertThat(neighbours.poll()).isEqualTo(Point(doubleArrayOf(0.0, 0.0)))
    }

    @Test
    fun ballTreePointsWithinRangeTest() {
        val points = listOf(
            Point(doubleArrayOf(0.0, 0.0)),
            Point(doubleArrayOf(1.0, 1.0)),
            Point(doubleArrayOf(0.5, 0.5)),
            Point(doubleArrayOf(-0.5, -0.5)),
            Point(doubleArrayOf(-1.0, -1.0))
        )
        val ballTree = BallTree(points, this::euclidianDistance, 50)
        val target = Point(doubleArrayOf(0.0, 0.0))
        val desiredRange = this.euclidianDistance(target, Point(doubleArrayOf(0.5, 0.5)))
        val pointsWithinRange = ballTree.getPointsWithinRange(target, desiredRange)

        Assertions.assertThat(pointsWithinRange).hasSize(3)
        Assertions.assertThat(pointsWithinRange).contains(Point(doubleArrayOf(0.0, 0.0)))
        Assertions.assertThat(pointsWithinRange).contains(Point(doubleArrayOf(0.5, 0.5)))
        Assertions.assertThat(pointsWithinRange).contains(Point(doubleArrayOf(-0.5, -0.5)))
    }
}