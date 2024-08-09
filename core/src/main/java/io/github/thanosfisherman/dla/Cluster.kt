package io.github.thanosfisherman.dla

import kotlin.math.max

class Cluster(val width: Float, val height: Float) {
    private var clusterRadius2 = 0f
    val particles: Array<Array<Particle>> = Pair(width.toInt(), height.toInt()).createArray(
        Particle(
            0f,
            0f
        )
    )
    var seed: Particle = Particle(width / 2, height / 2)
        set(value) {
            if (attach(value))
                field = value
        }

    fun attach(particle: Particle): Boolean {
        val attachX = particle.x.toInt()
        val attachY = particle.y.toInt()
        particles[attachX][attachY] = particle
        updateClusterRadius(particle)
        //check bounds?
        return true
    }

    private fun updateClusterRadius(leaf: Particle) {
        val dx = leaf.x - seed.x
        val dy = leaf.y - seed.y
        val dist2 = dx * dx + dy * dy
        clusterRadius2 = max(clusterRadius2, dist2)
    }
}