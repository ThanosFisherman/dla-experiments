package io.github.thanosfisherman.dla.spawn

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Config.RADIUS
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.randomFloatRange

class RandomSpawnStrategy : SpawnStrategy {
    override fun spawn(cluster: Cluster): Particle {
        val width = cluster.width - RADIUS
        val height = cluster.height - RADIUS

        val x = randomFloatRange(0f + RADIUS, width)
        val y = randomFloatRange(0f + RADIUS, height)
        return Particle(x, y)
    }
}
