package io.github.thanosfisherman.dla.walk

import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.walk.Direction.*

class RandomWalkStrategy: WalkStrategy {
    override fun walk(particle: Particle) {
        val dir = Direction.randomDirection()
        when (dir) {
            UP -> particle.y += 1
            RIGHT -> particle.x += 1
            DOWN -> particle.y -= 1
            LEFT -> particle.x -= 1
        }
    }
}
