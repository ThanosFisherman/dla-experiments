package io.github.thanosfisherman.dla.seed

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Particle

class CenterInitialSeedStrategy : InitialSeedStrategy {
    override fun seed(cluster: Cluster) {
        val width = cluster.width
        val height = cluster.height
        val particle = Particle(width / 2, height / 2)
        cluster.seedParticle = particle
    }
}