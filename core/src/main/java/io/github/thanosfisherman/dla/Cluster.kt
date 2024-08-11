package io.github.thanosfisherman.dla

import kotlin.math.floor

class Cluster(val width: Float, val height: Float) {
    private val walkers = mutableListOf<Particle>()
    private val dendrite = mutableListOf<Particle>()
     val treeMin = Particle(width,height)
     val treeMax = Particle(0f,0f)

    // val particles: Array<Array<Particle?>> = Pair(width.toInt(), height.toInt()).createArray(null)
    var seedParticle: Particle = Particle(width / 2, height / 2)
        set(value) {
            if (attach(value))
                field = value
        }

    private fun dist2(particle1: Particle, particle2: Particle): Float {
        val dx = particle1.x - particle2.x
        val dy = particle1.y - particle2.y
        return dx * dx + dy * dy
    }


    fun isContains(particle: Particle): Boolean {
        return treeMin.x < particle.x + particle.r && treeMax.x > particle.x - particle.r && treeMin.y < particle.y + particle.r && treeMax.y > particle.y - particle.r
    }

    fun walkers(): List<Particle> = walkers

    fun dendrite(): List<Particle> = dendrite

    fun addWalker(particle: Particle) {
        walkers.add(particle)
    }

    fun attach(particle: Particle): Boolean {
        dendrite.add(particle)
        walkers.remove(particle)
        if (particle.x - particle.r < treeMin.x) {
            treeMin.x = particle.x - particle.r
        }
        if (particle.y - particle.r < treeMin.y) {
            treeMin.y = particle.y - particle.r
        }
        if (particle.x + particle.r > treeMax.x) {
            treeMax.x = particle.x + particle.r
        }
        if (particle.y + particle.r > treeMax.y) {
            treeMax.y = particle.y + particle.r
        }
        return true
    }

    fun canAttach(particle: Particle): Boolean {

        dendrite.forEach {
            val d2 = dist2(particle, it)
            if (d2 <= (particle.r + it.r) * (particle.r + it.r))
                return true
        }
        return false
    }
}