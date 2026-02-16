package io.github.thanosfisherman.dla.spawn

import io.github.thanosfisherman.dla.dlaalgo.Cluster
import io.github.thanosfisherman.dla.utils.Juniper
import io.github.thanosfisherman.dla.Particle

class RandomAllOverSpawnStrategy : SpawnStrategy {

    override fun spawn(cluster: Cluster): Particle {
        val width = cluster.width
        val height = cluster.height

        val x = Juniper.random.nextFloat(0f, width)
        val y = Juniper.random.nextFloat(0f, height)
        val particle = Particle(x, y)

        return particle
    }
}