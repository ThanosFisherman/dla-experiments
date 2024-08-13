package io.github.thanosfisherman.dla.seed

import com.badlogic.gdx.math.MathUtils
import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Particle

class RandomInitialSeedStrategy : InitialSeedStrategy {
    override fun seed(cluster: Cluster) {
        val width = cluster.width
        val height = cluster.height

        val x = MathUtils.random(0f, width)
        val y = MathUtils.random(0f, height)
        val particle = Particle(x, y)
        cluster.seedParticle = particle
    }
}