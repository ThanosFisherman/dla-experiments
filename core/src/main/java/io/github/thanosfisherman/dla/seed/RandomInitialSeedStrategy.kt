package io.github.thanosfisherman.dla.seed

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Juniper
import io.github.thanosfisherman.dla.Particle

class RandomInitialSeedStrategy : InitialSeedStrategy {
    override fun seed(cluster: Cluster) {
        val width = cluster.width
        val height = cluster.height
        val centerProbability = Juniper.random.nextFloat() <= 0.6f

        val particle = if (centerProbability) {
            Particle(width / 2, height / 2)
        } else {
            val x = Juniper.random.nextFloat(0f, width)
            val y = Juniper.random.nextFloat(0f, height)
            Particle(x, y)
        }

        cluster.seedParticle = particle
    }
}
