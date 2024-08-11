package io.github.thanosfisherman.dla.seed

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.randomIntRange

class RandomInitialSeedStrategy : InitialSeedStrategy {
    override fun seed(cluster: Cluster) {
        val width = cluster.width
        val height = cluster.height

        val x = randomIntRange(0, width.toInt()).toFloat()
        val y = randomIntRange(0, height.toInt()).toFloat()
        val particle = Particle(x, y)
        cluster.seedParticle = particle
    }
}