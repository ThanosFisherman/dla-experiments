package io.github.thanosfisherman.dla.spawn

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Config.RADIUS
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.randomIntRange

class RandomSpawnStrategy : SpawnStrategy {
    override fun spawn(cluster: Cluster): Particle {
        val width = cluster.width - RADIUS
        val height = cluster.height - RADIUS

        val x = randomIntRange((0 + RADIUS).toInt(), (width).toInt()).toFloat()
        val y = randomIntRange((0 + RADIUS).toInt(), (height).toInt()).toFloat()
        return Particle(x, y)
    }
}
