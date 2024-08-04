package io.github.thanosfisherman.dla.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.github.thanosfisherman.dla.FrameRate
import io.github.thanosfisherman.dla.Simulation
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use

class FirstScreen : KtxScreen {
    private val gameViewport = FitViewport(900f, 900f)
    private val uiViewport = ScreenViewport()
    private val batch = SpriteBatch()
    private val shape = ShapeRenderer()
    private val fps = FrameRate()
    private lateinit var simulation: Simulation


    override fun show() {
        simulation = Simulation(gameViewport.worldWidth, gameViewport.worldHeight)
    }

    override fun render(delta: Float) {
        super.render(delta)
        clearScreen(red = 0.0f, green = 0.0f, blue = 0.0f)

        fps.update(delta)
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()

        gameViewport.apply()
        shape.projectionMatrix = gameViewport.camera.combined
        shape.color = Color.CYAN
        shape.begin(ShapeRenderer.ShapeType.Filled)
        simulation.update()
        simulation.draw(shape)
        shape.end()


        uiViewport.apply()
        batch.projectionMatrix = uiViewport.camera.combined
        batch.use {
            fps.render(it, height = uiViewport.worldHeight)
        }
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }

    override fun dispose() {
        batch.disposeSafely()
        fps.dispose()
        shape.dispose()
    }
}