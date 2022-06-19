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
        Assertions.assertThat(neighbours).contains(Point(doubleArrayOf(0.5, 0.5)), Point(doubleArrayOf(0.0, 0.0)))
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

    @Test
    fun ballTreePointsHaversineDistanceTest() {
        val portlouis = Point(doubleArrayOf(-20.1609, 57.5012))
        val grandbaie = Point(doubleArrayOf(-20.0089, 57.5816))
        val paris = Point(doubleArrayOf(48.8566, 2.3522))
        val lyon = Point(doubleArrayOf(45.7640, 4.8357))
        val nice = Point(doubleArrayOf(43.7102, 7.2620))
        val bordeaux = Point(doubleArrayOf(44.8378, -0.5792))
        val london = Point(doubleArrayOf(51.5072, -0.1276))
        val birmingham = Point(doubleArrayOf(52.4862, -1.8904))
        val edinburgh = Point(doubleArrayOf(55.9533, -3.1883))
        val newyork = Point(doubleArrayOf(40.7128, -74.0060))
        val seattle = Point(doubleArrayOf(47.6062, -122.3321))
        val toronto = Point(doubleArrayOf(43.6532, -79.3832))
        val vancouver = Point(doubleArrayOf(49.2827, -123.1207))
        val beijing = Point(doubleArrayOf(39.9042, 116.4074))
        val shanghai = Point(doubleArrayOf(31.2304, 121.4737))
        val seoul = Point(doubleArrayOf(37.5665, 126.9780))
        val busan = Point(doubleArrayOf(35.1796, 129.0756))
        val tokyo = Point(doubleArrayOf(35.6762, 139.6503))
        val osaka = Point(doubleArrayOf(34.6937, 135.5023))
        val sydney = Point(doubleArrayOf(-33.8688, 151.2093))
        val melbourne = Point(doubleArrayOf(-37.8136, 144.9631))
        val perth = Point(doubleArrayOf(-31.9523, 115.8613))

        val points = listOf(
            portlouis,
            grandbaie,
            paris,
            lyon,
            nice,
            bordeaux,
            london,
            birmingham,
            edinburgh,
            newyork,
            seattle,
            toronto,
            vancouver,
            beijing,
            shanghai,
            seoul,
            busan,
            tokyo,
            osaka,
            sydney,
            melbourne,
            perth
        )
        val ballTree = BallTree(points, this::haversineDistance, 5)
        val madrid = Point(doubleArrayOf(40.4168, -3.7038))
        val range = 600 * 1000.0 // 600 kilometer
        val cities600KmFromMadrid = ballTree.getPointsWithinRange(madrid, range)
        val madridClosestNeighbours = ballTree.getKNearestNeighbours(madrid, 3)

        Assertions.assertThat(cities600KmFromMadrid).hasSameElementsAs(listOf(bordeaux))
        Assertions.assertThat(madridClosestNeighbours).contains(nice, bordeaux, lyon)
    }
}