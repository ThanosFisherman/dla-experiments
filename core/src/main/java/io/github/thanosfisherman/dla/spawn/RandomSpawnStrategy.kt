package io.github.thanosfisherman.dla.spawn

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.RADIUS
import io.github.thanosfisherman.dla.randomFloatRange

class RandomSpawnStrategy : SpawnStrategy {
    override fun spawn(cluster: Cluster): Particle {
        val width = cluster.width
        val height = cluster.height

        val x = randomFloatRange(0f + RADIUS, width - RADIUS)
        val y = randomFloatRange(0f + RADIUS, height - RADIUS)
        return Particle(x, y)
    }
}
