package io.github.thanosfisherman.dla

import io.github.thanosfisherman.dla.Config.toIndex

class Cluster(val width: Float, val height: Float) {
    private val walkers = mutableListOf<Particle>()
    private val dendrite = mutableListOf<Particle>()
    private var age = 0

    val bottomLeft = Particle(width, height)
    val topRight = Particle(0f, 0f)

    private val cols = toIndex(width)
    private val rows = toIndex(height)

    //Kotlin goes nuts with multidimensional arrays
    private val particles: Array<Array<ArrayList<Particle>>> = Array(cols) { Array(rows) { ArrayList() } }

    var seedParticle: Particle = Particle(width / 2, height / 2)
        set(value) {
            attach(value)
            field = value
        }

    fun walkers(): List<Particle> = walkers

    fun dendrite(): List<Particle> = dendrite

    fun addWalker(particle: Particle) {
        walkers.add(particle)
    }

    fun removeWalker(particle: Particle) {
        //walkers.removeValue(particle, false)
        walkers.remove(particle)
    }

    fun isContained(particle: Particle): Boolean {
        return (bottomLeft.x < particle.x + particle.r && bottomLeft.y < particle.y + particle.r && topRight.x > particle.x - particle.r && topRight.y > particle.y - particle.r)
    }

    fun attach(particle: Particle) {
        particles[toIndex(particle.x)][toIndex(particle.y)].add(particle)
        dendrite.add(particle)
        age++

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
        val col = toIndex(particle.x)
        val row = toIndex(particle.y)
        if (!isExceedGridBounds(col, row)) return false
        for (i in (-1..1)) {
            for (j in (-1..1)) {
                val walkersInCell = particles[col + i][row + j]
                for (walker in walkersInCell) {
                    val d2 = particle.dist2(walker)
                    if (d2 < (particle.r + walker.r) * (particle.r + walker.r)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun isExceedGridBounds(col: Int, row: Int): Boolean {
        return col > 1 && row > 1 && col < cols - 1 && row < rows - 1
    }
}
