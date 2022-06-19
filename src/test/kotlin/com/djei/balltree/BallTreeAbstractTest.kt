package com.djei.balltree

import kotlin.math.*

abstract class BallTreeAbstractTest {
    fun euclidianDistance(pointA: Point, pointB: Point): Double {
        return sqrt(pointA.coordinates.zip(pointB.coordinates).sumOf { (it.first - it.second).pow(2) })
    }

    fun haversineDistance(pointA: Point, pointB: Point): Double {
        val latA = pointA.coordinates[0]
        val lonA = pointA.coordinates[1]
        val latB = pointB.coordinates[0]
        val lonB = pointB.coordinates[1]

        // Distance between latitudes and longitudes
        val dLat = Math.toRadians(latB - latA)
        val dLon = Math.toRadians(lonB - lonA)

        // Convert latitude to radians
        val latARadian = Math.toRadians(latA)
        val latBRadian = Math.toRadians(latB)

        // Apply Haversine formula
        val a = sin(dLat / 2).pow(2.0) +
                sin(dLon / 2).pow(2.0) *
                cos(latARadian) *
                cos(latBRadian)
        val c = 2 * asin(sqrt(a))
        val earthRadius = 6371000.0
        return earthRadius * c
    }
}