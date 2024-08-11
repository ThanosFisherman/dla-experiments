package io.github.thanosfisherman.dla.seed

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.randomFloatRange

class RandomInitialSeedStrategy : InitialSeedStrategy {
    override fun seed(cluster: Cluster) {
        val width = cluster.width
        val height = cluster.height

        val x = randomFloatRange(0f, width)
        val y = randomFloatRange(0f, height)
        val particle = Particle(x, y)
        cluster.seedParticle = particle
    }
}