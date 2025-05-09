package io.github.thanosfisherman.dla.walk

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Config.RADIUS
import io.github.thanosfisherman.dla.Config.SPEED
import io.github.thanosfisherman.dla.Juniper
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.constrainX
import io.github.thanosfisherman.dla.constrainY

class RandomWalkStrategy(private val cluster: Cluster) : WalkStrategy {
    override fun walk(particle: Particle) {

        val width = cluster.width
        val height = cluster.height
        val x = Juniper.random.nextFloat(-SPEED, SPEED)
        val y = Juniper.random.nextFloat(-SPEED, SPEED)
        val xmin = cluster.bottomLeft.x  - 80
        val xmax = cluster.topRight.x + 80
        val ymin = cluster.bottomLeft.y - 65
        val ymax = cluster.topRight.y + 65
        particle.x += x
        particle.y += y
        particle.constrainX(xmin, xmax)
        particle.constrainY(ymin, ymax)
    }
}
