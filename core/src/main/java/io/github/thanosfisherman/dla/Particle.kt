package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.cos
import com.badlogic.gdx.math.MathUtils.sin
import com.badlogic.gdx.math.Vector2
import io.github.thanosfisherman.dla.dlaalgo.Config.RADIUS
import ktx.math.vec2
import kotlin.math.sqrt

data class Particle(
    var x: Float,
    var y: Float,
    val r: Float = RADIUS,
) {
    var lifeTime: Int = 0
}

internal fun Particle.draw(shape: ShapeRenderer, color: Color = Color.WHITE) {
    shape.color = color
    shape.circle(x, y, r, 8)
//    shape.rect(x - r, y - r, 2 * r, 2 * r)
    //shape.point(x+r, y+r, 0f)
}

internal fun Vector2.draw(shape: ShapeRenderer, color: Color = Color.WHITE) {
    shape.color = color
    shape.circle(x, y, RADIUS, 8)
}

internal fun Particle.constrainX(low: Float, high: Float) {
    if (this.x < low) this.x = low
    if (this.x > high) this.x = high
}

internal fun Particle.constrainY(low: Float, high: Float) {
    if (this.y < low) this.y = low
    if (this.y > high) this.y = high
}

internal fun Particle.constrainAngleRad(angle: Float, minAngle: Float, maxAngle: Float) {
    val vector = vec2(x, y)
    vector.constrainAngleRad(angle, minAngle, maxAngle)
    x = vector.x
    y = vector.y
}

internal fun Particle.angleRad(referenceX: Float, referenceY: Float): Float {
    val vector = vec2(x, y)
    return vector.angleRad(vec2(referenceX, referenceY))
}

internal fun Vector2.constrainAngleRad(angle: Float, minAngle: Float, maxAngle: Float) {
    val mag = len()
    if (angle < minAngle) {
        setAngleRad(minAngle)
    } else if (angle > maxAngle) {
        setAngleRad(maxAngle)
    } else {
        setAngleRad(angle)
    }
    setLength(mag)
}

internal fun Vector2.fromAngle(angle: Float): Vector2 {
    set(cos(angle), sin(angle))
    return this
}

internal fun Particle.dist2(other: Particle): Float {
    val dx = x - other.x
    val dy = y - other.y
    return dx * dx + dy * dy
}

internal fun Particle.overlaps(other: Particle): Boolean {
    val distanceSquared = dist2(other)
    val minDistance = (RADIUS * 2) / 3
    return distanceSquared < minDistance * minDistance
}

internal fun Particle.dist(other: Particle): Float {
    val dx = x - other.x
    val dy = y - other.y
    return sqrt(dx * dx + dy * dy)
}

internal fun Vector2.reflectDeg(degrees: Int): Vector2 {
    return reflectRad(degrees * MathUtils.degreesToRadians)
}

internal fun Vector2.reflectRad(angleRad: Float): Vector2 {

    val cos = MathUtils.cos(2 * angleRad)
    val sin = MathUtils.sin(2 * angleRad)

    val newX = this.x * cos + this.y * sin
    val newY = this.x * sin - this.y * cos

    this.x = newX
    this.y = newY
    return this
}

internal fun Vector2.reflectAroundDeg(reference: Vector2, degrees: Int): Vector2 {
    this.sub(reference).reflectDeg(degrees).add(reference)
    return this
}

internal fun Particle.reflectRad(angleRad: Float): Particle {
    val vec = vec2(x, y)
    // val reference = vec2(referenceX, referenceY)
    vec.reflectRad(angleRad)
    return Particle(vec.x, vec.y)
}

internal fun Particle.rotateRad(angleRad: Float): Particle {
    val vec = vec2(x, y)
    // val reference = vec2(referenceX, referenceY)
    vec.rotateRad(angleRad)
    return Particle(vec.x, vec.y)
}

internal fun Particle.reflectDeg(angleDeg: Float): Particle {
    return reflectRad(angleDeg * MathUtils.degreesToRadians)
}

internal fun Particle.rotateDeg(angleDeg: Float): Particle {
    return rotateRad(angleDeg * MathUtils.degreesToRadians)
}