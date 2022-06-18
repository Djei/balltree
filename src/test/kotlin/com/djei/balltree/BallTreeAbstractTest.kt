package com.djei.balltree

import kotlin.math.pow
import kotlin.math.sqrt

abstract class BallTreeAbstractTest {
    fun euclidianDistance(pointA: Point, pointB: Point): Double {
        return sqrt(pointA.coordinates.zip(pointB.coordinates).sumOf { (it.first - it.second).pow(2) })
    }
}