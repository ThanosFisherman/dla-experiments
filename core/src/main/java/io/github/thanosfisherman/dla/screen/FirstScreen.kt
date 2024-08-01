package io.github.thanosfisherman.dla.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.github.thanosfisherman.dla.FrameRate
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.rect
import ktx.graphics.use
import ktx.math.vec2

class FirstScreen : KtxScreen {
    private val gameViewport = FitViewport(900f, 900f)
    private val uiViewport = ScreenViewport()
    private val batch = SpriteBatch()
    private val shape = ShapeRenderer()
    private val fps = FrameRate()
    private var time = 0f

    override fun show() {

    }

    override fun render(delta: Float) {
        super.render(delta)
        clearScreen(red = 0.0f, green = 0.0f, blue = 0.0f)

        fps.update(delta)
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()

        gameViewport.apply()
        shape.projectionMatrix = gameViewport.camera.combined

        //for collision detection between points
        val point = vec2(30f,30f)
        point.setLength(30f)
//        point.epsilonEquals()
        gl.glLineWidth(5f)
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.point(200f,200f,0f)
        shape.point(200f,201f,0f)
        shape.point(200f,202f,0f)
        shape.point(200f,203f,0f)
        shape.point(200f,204f,0f)
        shape.rect(point,3f,3f)
        shape.end()


        uiViewport.apply()
        batch.projectionMatrix = uiViewport.camera.combined
        batch.use {
            fps.render(it, height = uiViewport.worldHeight)
        }
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height,true)
        uiViewport.update(width, height, true)
    }
    override fun dispose() {
        batch.disposeSafely()
        fps.dispose()
        shape.dispose()
    }
}