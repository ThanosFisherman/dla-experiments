package io.github.thanosfisherman.dla.walk

import io.github.thanosfisherman.dla.Config.SPEED
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.randomFloatRange

class RandomWalkStrategy(private val width: Float, private val height: Float) : WalkStrategy {
    override fun walk(particle: Particle) {
        //val v = random2D()
        val x = randomFloatRange(-SPEED, SPEED)
        val y = randomFloatRange(-SPEED, SPEED)
        particle.x += x
        particle.y += y
    }
}
