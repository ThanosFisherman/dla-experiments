package io.github.thanosfisherman.dla

import ktx.collections.GdxArray

class Cluster(val width: Float, val height: Float) {
    private val walkers = GdxArray<Particle>(false, 1000)
    private val dendrite = GdxArray<Particle>(false, 1000)

    val bottomLeft = Particle(width, height)
    val topRight = Particle(0f, 0f)

    // val particles: Array<Array<Particle?>> = Pair(width.toInt(), height.toInt()).createArray(null)
    var seedParticle: Particle = Particle(width / 2, height / 2)
        set(value) {
            attach(value)
            field = value
        }

    fun walkers(): GdxArray<Particle> = walkers

    fun dendrite(): GdxArray<Particle> = dendrite

    fun addWalker(particle: Particle) {
        walkers.add(particle)
    }

    fun isContained(particle: Particle): Boolean {
        return (bottomLeft.x < particle.x + particle.r && bottomLeft.y < particle.y + particle.r && topRight.x > particle.x - particle.r && topRight.y > particle.y - particle.r)
    }

    fun attach(particle: Particle) {
        dendrite.add(particle)
        walkers.removeValue(particle, false)

        if (particle.x - particle.r < bottomLeft.x)
            bottomLeft.x = particle.x - particle.r

        if (particle.y - particle.r < bottomLeft.y)
            bottomLeft.y = particle.y - particle.r

        if (particle.x + particle.r > topRight.x)
            topRight.x = particle.x + particle.r

        if (particle.y + particle.r > topRight.y)
            topRight.y = particle.y + particle.r
    }

    fun canAttach(particle: Particle): Boolean {

//        val iter = dendrite.iterator()
//        while (iter.hasNext()) {
//            val next = iter.next()
//            val d2 = particle.dist2(next)
//            if (d2 <= (particle.r + next.r) * (particle.r + next.r))
//                return true
//        }
        dendrite.forEach {
            val d2 = particle.dist2(it)
            if (d2 <= (particle.r + it.r) * (particle.r + it.r))
                return true
        }
        return false
    }
}