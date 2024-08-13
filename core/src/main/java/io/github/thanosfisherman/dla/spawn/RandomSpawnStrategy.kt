package io.github.thanosfisherman.dla.spawn

import com.badlogic.gdx.math.MathUtils
import io.github.thanosfisherman.dla.Cluster
import io.github.thanosfisherman.dla.Particle

class RandomSpawnStrategy : SpawnStrategy {
    override fun spawn(cluster: Cluster): Particle {
        val width = cluster.width
        val height = cluster.height

        val i: Int = MathUtils.floor(MathUtils.random(4f))
        return when (i) {
            0 -> Particle(MathUtils.random(width), 0f)
            1 -> Particle(0f, MathUtils.random(height))
            2 -> Particle(MathUtils.random(width), height)
            else -> Particle(width, MathUtils.random(height))
        }
    }
}
