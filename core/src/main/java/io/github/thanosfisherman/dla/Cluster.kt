package io.github.thanosfisherman.dla

class Cluster(val width: Float, val height: Float) {
    private val walkers = mutableListOf<Particle>()
    private val dendrite = mutableListOf<Particle>()

    val bottomLeft = Particle(width, height)
    val topRight = Particle(0f, 0f)

    // val particles: Array<Array<Particle?>> = Pair(width.toInt(), height.toInt()).createArray(null)
    var seedParticle: Particle = Particle(width / 2, height / 2)
        set(value) {
            attach(value)
            field = value
        }

    private fun dist2(particle1: Particle, particle2: Particle): Float {
        val dx = particle1.x - particle2.x
        val dy = particle1.y - particle2.y
        return dx * dx + dy * dy
    }

    fun walkers(): List<Particle> = walkers

    fun dendrite(): List<Particle> = dendrite

    fun addWalker(particle: Particle) {
        walkers.add(particle)
    }

    fun isContained(particle: Particle): Boolean {
        return (bottomLeft.x < particle.x + particle.r && bottomLeft.y < particle.y + particle.r && topRight.x > particle.x - particle.r && topRight.y > particle.y - particle.r)
    }

    fun attach(particle: Particle) {
        dendrite.add(particle)
        walkers.remove(particle)

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

        dendrite.forEach {
            val d2 = dist2(particle, it)
            if (d2 <= (particle.r + it.r) * (particle.r + it.r))
                return true
        }
        return false
    }
}