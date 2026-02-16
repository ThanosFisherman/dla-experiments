package io.github.thanosfisherman.dla.dlaalgo

import io.github.thanosfisherman.dla.dlaalgo.Config.toIndex
import io.github.thanosfisherman.dla.Particle
import ktx.log.logger

private val logger = logger<Cluster>()
class Cluster(val width: Float, val height: Float) {
    val walkers = mutableListOf<Particle>()
    val dendrite = mutableListOf<Particle>()

    val bottomLeft = Particle(width, height)
    val topRight = Particle(0f, 0f)

    private val cols = toIndex(width)
    private val rows = toIndex(height)

    //Kotlin goes nuts with multidimensional arrays
    private val particles: Array<Array<MutableList<Particle>>> = Array(cols) { Array(rows) { mutableListOf() } }

    var seedParticle: Particle = Particle(width / 2, height / 2)
        set(value) {
            attach(value)
            field = value
        }

    fun addWalker(particle: Particle) {
        walkers.add(particle)
    }

    fun removeWalker(particle: Particle) {
        walkers.remove(particle)
    }

    fun removeFromDendrite(particle: Particle) {
        dendrite.remove(particle)
    }

    fun isContained(particle: Particle): Boolean {
        return (bottomLeft.x < particle.x + particle.r && bottomLeft.y < particle.y + particle.r && topRight.x > particle.x - particle.r && topRight.y > particle.y - particle.r)
    }

    fun attach(particle: Particle) {
        val x = toIndex(particle.x)
        val y = toIndex(particle.y)
        particles[x][y].add(particle)
        dendrite.add(particle)

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
//        for (i in (-1..1)) {
//            for (j in (-1..1)) {
//                val walkersInCell = particles[col + i][row + j]
//                for (walker in walkersInCell) {
//                    val d2 = particle.dist2(walker)
//                    if (d2 < (particle.r + walker.r) * (particle.r + walker.r)) {
//                        return true
//                    }
//                }
//            }
//        }

        return Neighbours.findNeighbouringCells(particle, col, row, particles)
    }

    private fun isExceedGridBounds(col: Int, row: Int): Boolean {
        return col > 1 && row > 1 && col < cols - 1 && row < rows - 1
    }
}
