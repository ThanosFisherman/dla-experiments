package io.github.thanosfisherman.dla.walk

import io.github.thanosfisherman.dla.*
import io.github.thanosfisherman.dla.Config.SPEED

class RandomWalkStrategy(private val cluster: Cluster) : WalkStrategy {
    override fun walk(particle: Particle) {

        val width = cluster.width
        val height = cluster.height
        val x = Juniper.random.nextFloat(-SPEED, SPEED)
        val y = Juniper.random.nextFloat(-SPEED, SPEED)
        val xmin = cluster.bottomLeft.x - 80
        val xmax = cluster.topRight.x + 80
        val ymin = cluster.bottomLeft.y - 80
        val ymax = cluster.topRight.y + 80
        particle.x += x
        particle.y += y
        particle.constrainX(xmin, xmax)
        particle.constrainY(ymin, ymax)
    }
}
