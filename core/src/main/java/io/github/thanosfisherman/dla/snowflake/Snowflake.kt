package io.github.thanosfisherman.dla.snowflake

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import io.github.thanosfisherman.dla.*
import ktx.math.vec2


class Snowflake(private val width: Float, private val height: Float) {

    private val snowflakes = mutableListOf<Particle>()
    private var particleVector = vec2((height / 2) - 10, 10f)
    private val color = Color.valueOf("#8de3fc").apply { a = 0.7f }

    fun update() {

        var counter = 0
        while (!isFinished(particleVector) && !isCollide(particleVector, snowflakes)) {
            updateParticleMovement()
            counter++
        }

        if (counter == 0) {
            return
        }

        snowflakes.add(Particle(particleVector.x, particleVector.y))
        particleVector = vec2((height / 2) - 10, Juniper.random.nextInt(10).toFloat())
    }

    fun draw(shape: ShapeRenderer) {

        snowflakes.forEach { part ->

            var p = part
            var refl: Particle
            repeat(6) {
                p = p.rotateRad(MathUtils.PI / 3)
                refl = p.reflectRad(MathUtils.PI / 3)
                val alignPart = p.rotateRad(MathUtils.PI / 6)
                val alignRefl = refl.rotateRad(MathUtils.PI / 6)
                alignPart.draw(shape, color)
                alignRefl.draw(shape, color)
            }
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
        return particle.x < 5
    }

    private fun updateParticleMovement() {

        particleVector.x -= 2f
        particleVector.y += Juniper.random.nextInt(-10, 10).toFloat()

        var angleRad = particleVector.angleRad()

        angleRad = MathUtils.clamp(angleRad, 0f, MathUtils.PI / 6)

        val mag = particleVector.len()

//        particleVector.setAngleRad(angleRad)
//        particleVector.setLength(mag)

        particleVector.x = mag * MathUtils.cos(angleRad)
        particleVector.y = mag * MathUtils.sin(angleRad)

        //println(vec.angleDeg())
    }
}