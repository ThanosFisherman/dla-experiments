package io.github.thanosfisherman.dla

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import ktx.assets.disposeSafely


class FrameRate : Disposable {
    var isRendered = false
    private var sinceChange = 0f
    private var frameRate: Float
    private val font: BitmapFont


    init {
        frameRate = Gdx.graphics.framesPerSecond.toFloat()
        font = BitmapFont()
        font.region.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
    }

    fun update(delta: Float) {
        if (!isRendered) return

        sinceChange += delta
        if (sinceChange >= 1f) {
            sinceChange = 0f
            frameRate = Gdx.graphics.framesPerSecond.toFloat()
        }
    }

    fun render(batch: SpriteBatch, walkers: Int, tree: Int, width: Float = 0f, height: Float = 16f) {
        if (!isRendered) return

        font.draw(batch, frameRate.toInt().toString() + " fps", width, height)
        font.draw(batch, "Walker count $walkers", width, height - 16)
        font.draw(batch, "Walkers on tree $tree", width, height - 32)
    }

    override fun dispose() {
        font.disposeSafely()
    }
}