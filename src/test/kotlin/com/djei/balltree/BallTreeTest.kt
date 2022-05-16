package com.djei.balltree

import org.junit.jupiter.api.Test
import kotlin.math.sqrt
import kotlin.random.Random

class BallTreeTest {

    @Test
    fun ballTreeConstructionPerformanceTest() {
        val random = Random(0)
        val points = mutableListOf<Point>()
        val dimensionality = 3
        val populationSize = 1000000
        for (i in 0 until populationSize) {
            val coordinates = DoubleArray(3)
            for (j in 0 until dimensionality) {
                coordinates[j] = random.nextDouble(populationSize.toDouble())
            }
            points.add(Point(coordinates))
        }

        BallTree(points, this::euclidianDistance, 50)
    }

    fun euclidianDistance(pointA: Point, pointB: Point): Double {
        return sqrt(pointA.coordinates.zip(pointB.coordinates).sumOf { it.first - it.second })
    }
}