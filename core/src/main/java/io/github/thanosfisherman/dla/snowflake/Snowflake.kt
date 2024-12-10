package io.github.thanosfisherman.dla.snowflake

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import io.github.thanosfisherman.dla.Config
import io.github.thanosfisherman.dla.Juniper
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.draw
import ktx.math.vec2


class Snowflake(private val width: Float, private val height: Float) {

    private val snowflakes = mutableListOf<Particle>()
    private var particleVector = vec2(1f, 0f)

    fun update() {

        while (!isFinished(particleVector) && !isCollide(particleVector, snowflakes)) {
            updateParticleMovement()
        }




        if (isFinished(particleVector) || isCollide(particleVector, snowflakes)) {
            snowflakes.add(Particle(particleVector.x, particleVector.y))
            particleVector = vec2(width / 2, Juniper.random.nextInt(30).toFloat())
        }


    }

    fun draw(shape: ShapeRenderer) {

        snowflakes.forEach {
            it.draw(shape)
        }
    }

    private fun isCollide(particle: Vector2, particles: List<Particle>): Boolean {
        for (part in particles) {
            val d2 = particle.dst2(part.x, part.y)
            if (d2 < (part.r + part.r) * (part.r + part.r)) {
                return true
            }
        }
        return false
    }

    private fun isFinished(particle: Vector2): Boolean {
        return particle.x < 10
    }

    private fun updateParticleMovement() {

        particleVector.x -= 2f
        particleVector.y += Juniper.random.nextInt(-15, 15).toFloat()

        var angleRad = particleVector.angleRad()

        angleRad = MathUtils.clamp(angleRad, 0f, MathUtils.PI / 6)

        val mag = particleVector.len()

        particleVector.setAngleRad(angleRad)
        particleVector.setLength(mag)

        //println(vec.angleDeg())
    }
}