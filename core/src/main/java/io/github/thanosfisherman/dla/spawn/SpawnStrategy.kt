package io.github.thanosfisherman.dla.spawn

import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Particle

interface SpawnStrategy {
    fun spawn(cluster: Cluster): Particle
}