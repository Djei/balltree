package com.djei.balltree

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class BallTreePerformanceTest : BallTreeAbstractTest() {

    @Test
    fun ballTreeConstructionPerformanceTest() {
        BallTree(generatePoints(), this::euclidianDistance, 500)
    }

    @Test
    fun ballTreeKNearestNeighboursPerformanceTest() {
        val points = generatePoints()
        val exhaustiveSearchBallTree = BallTree(points, this::euclidianDistance, 0)
        val ballTreeSearchBallTree = BallTree(points, this::euclidianDistance, 500)
        val target = Point(doubleArrayOf(50.0, 50.0))

        val exhaustiveSearchStartTime = System.currentTimeMillis()
        val exhaustiveSearchNeighbours = exhaustiveSearchBallTree.getKNearestNeighbours(target, 1000)
        val exhaustiveSearchDuration = System.currentTimeMillis() - exhaustiveSearchStartTime
        val ballTreeSearchStartTime = System.currentTimeMillis()
        val ballTreeSearchNeighbours = ballTreeSearchBallTree.getKNearestNeighbours(target, 1000)
        val ballTreeSearchDuration = System.currentTimeMillis() - ballTreeSearchStartTime

        Assertions.assertThat(exhaustiveSearchNeighbours).hasSize(1000)
        Assertions.assertThat(ballTreeSearchNeighbours).hasSize(1000)
        Assertions.assertThat(exhaustiveSearchNeighbours).hasSameElementsAs(ballTreeSearchNeighbours)
        Assertions.assertThat(exhaustiveSearchDuration).isGreaterThan(ballTreeSearchDuration)
    }

    private fun generatePoints(): List<Point> {
        val random = Random(0)
        val points = mutableListOf<Point>()
        val dimensionality = 2
        val populationSize = 750000
        for (i in 0 until populationSize) {
            val coordinates = DoubleArray(2)
            for (j in 0 until dimensionality) {
                coordinates[j] = random.nextDouble(100.0)
            }
            points.add(Point(coordinates))
        }
        return points
    }
}