package io.github.thanosfisherman.dla

import kotlin.math.max
import kotlin.math.min

class Cluster(val width: Float, val height: Float) {
    private var clusterRadius2 = 0f
    val particles: Array<Array<Particle?>> = Pair(width.toInt(), height.toInt()).createArray(null)
    var seed: Particle = Particle(width / 2, height / 2)
        set(value) {
            println("ATTACH")
            if (attach(value))
                field = value
        }

    fun attach(particle: Particle): Boolean {
        val attachX = particle.x.toInt()
        val attachY = particle.y.toInt()

        if (particles[attachX][attachY] == null) {
            particles[attachX][attachY] = particle
            updateClusterRadius(particle)
            return true
        }
        return false
    }

    fun canAttach(particle: Particle): Boolean {
        val x = particle.x.toInt()
        val y = particle.y.toInt()
        val neighbour = arrayOfNulls<Particle>(4)

        /**
         * 3 x x x  2,3
         * 2 x x x
         * 1 x x x
         * 0 1 2 3
         */

        neighbour[0] = particles[x][max((y - 1), 0)] // below
        neighbour[1] = particles[min((x + 1), (width.toInt() - 1))][y] //right
        neighbour[2] = particles[x][min((y + 1), (height.toInt() - 1))] //above
        neighbour[3] = particles[max((x - 1), 0)][y] //left

        return neighbour.any { it != null }
    }


    private fun updateClusterRadius(leaf: Particle) {
        val dx = leaf.x - seed.x
        val dy = leaf.y - seed.y
        val dist2 = dx * dx + dy * dy
        clusterRadius2 = max(clusterRadius2, dist2)
    }
}