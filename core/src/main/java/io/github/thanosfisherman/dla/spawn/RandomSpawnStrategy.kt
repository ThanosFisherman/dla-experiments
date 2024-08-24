package io.github.thanosfisherman.dla.spawn

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Config.RADIUS
import io.github.thanosfisherman.dla.Juniper
import io.github.thanosfisherman.dla.Particle

class RandomSpawnStrategy : SpawnStrategy {
    override fun spawn(cluster: Cluster): Particle {
        val width = cluster.width
        val height = cluster.height

        val i: Int = Juniper.random.nextInt(4)
        val randomRadius = RADIUS //Juniper.random.nextFloat(0.8f, RADIUS)
        return when (i) {
            0 -> Particle(Juniper.random.nextFloat(width), 0f, randomRadius)
            1 -> Particle(0f, Juniper.random.nextFloat(height), randomRadius)
            2 -> Particle(Juniper.random.nextFloat(width), height, randomRadius)
            else -> Particle(width, Juniper.random.nextFloat(height), randomRadius)
        }
    }
}
