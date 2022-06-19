package com.djei.balltree

data class Point(val coordinates: DoubleArray, val metadata: Map<String, String>? = null) {
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