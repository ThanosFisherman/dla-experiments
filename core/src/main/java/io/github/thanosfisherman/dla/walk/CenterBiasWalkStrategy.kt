package io.github.thanosfisherman.dla.walk

import com.badlogic.gdx.math.MathUtils.*
import io.github.thanosfisherman.dla.Particle

class CenterBiasWalkStrategy(private val width: Float, private val height: Float) : WalkStrategy {
    override fun walk(particle: Particle) {
        val angle = atan2((height / 2) - particle.y, (width / 2) - particle.x)
        particle.x += cos(angle)
        particle.y += sin(angle)
    }
}