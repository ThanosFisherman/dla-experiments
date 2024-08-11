package io.github.thanosfisherman.dla

import io.github.thanosfisherman.dla.Config.RADIUS
import kotlin.math.max
import kotlin.math.min

class Cluster(val width: Float, val height: Float) {
    private val walkers = mutableListOf<Particle>()
    private val dendrite = mutableListOf<Particle>()

    private var clusterRadius2 = RADIUS * RADIUS
    val particles: Array<Array<Particle?>> = Pair(width.toInt(), height.toInt()).createArray(null)
    var seedParticle: Particle = Particle(width / 2, height / 2)
        set(value) {
            walkers.add(value)
            if (attach(value))
                field = value
        }

    private fun dist2(particle1: Particle, particle2: Particle): Float {
        val dx = particle1.x - particle2.x
        val dy = particle1.y - particle2.y
        return dx * dx + dy * dy
    }

    fun isWithinClusterRadius(particle: Particle): Boolean {
        val d2 = dist2(particle, seedParticle)
        if (d2 <= clusterRadius2) {
            return true
        }
        return false
    }

    fun walkers(): List<Particle> = walkers

    fun addWalker(particle: Particle) {
        walkers.add(particle)
    }

    fun attach(particle: Particle): Boolean {
        val attachX = particle.x.toInt()
        val attachY = particle.y.toInt()

        if (particles[attachX][attachY] == null) {
            dendrite.add(particle)
            particles[attachX][attachY] = particle
            updateClusterRadius(particle)
            walkers.remove(particle)
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
        val can = neighbour[0] != null || neighbour[1] != null || neighbour[2] != null || neighbour[3] != null
        return can
    }


    private fun updateClusterRadius(leaf: Particle) {
        val d2 = dist2(leaf, seedParticle)
        clusterRadius2 = max(clusterRadius2, d2)
    }
}