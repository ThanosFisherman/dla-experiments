package io.github.thanosfisherman.dla.seed

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Config.RADIUS
import io.github.thanosfisherman.dla.Juniper
import io.github.thanosfisherman.dla.Particle

class TopRightSeedStrategy : InitialSeedStrategy {
    override fun seed(cluster: Cluster) {
        val width = cluster.width
        val height = cluster.height
            val x = width - 4
            val y = height - 4
            val particle = Particle(x, y)


        cluster.seedParticle = particle
    }
}
