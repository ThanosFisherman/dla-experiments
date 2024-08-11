package io.github.thanosfisherman.dla.walk

import io.github.thanosfisherman.dla.Config.SPEED
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.constrain
import io.github.thanosfisherman.dla.randomIntRange

class RandomWalkStrategy(private val width: Float, private val height: Float) : WalkStrategy {
    override fun walk(particle: Particle) {
        val x = randomIntRange(-SPEED, SPEED).toFloat()
        val y = randomIntRange(-SPEED, SPEED).toFloat()
        particle.x += x
        particle.y += y
        particle.constrain(width, height)
    }
}
