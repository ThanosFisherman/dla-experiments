package io.github.thanosfisherman.dla.walk

import io.github.thanosfisherman.dla.Particle

/**
 * Walking strategy
 */
interface WalkStrategy {
    fun walk(particle: Particle)
}