package io.github.thanosfisherman.dla.screen

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.PixmapIO
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.github.thanosfisherman.dla.FrameRate
import io.github.thanosfisherman.dla.Simulation
import io.github.thanosfisherman.dla.seed.RandomInitialSeedStrategy
import io.github.thanosfisherman.dla.spawn.RandomAllOverSpawnStrategy
import io.github.thanosfisherman.dla.walk.RandomWalkStrategy
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import java.util.zip.Deflater

class FirstScreen : KtxScreen {
    private val gameViewport = ScreenViewport()
    private val uiViewport = ScreenViewport()
    private val batch = SpriteBatch()
    private val shape = ShapeRenderer()
    private val fps = FrameRate()
    private lateinit var simulation: Simulation
    private var simStarted = false

//    private val vector = vec3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())


    override fun show() {
        Gdx.input.inputProcessor = object : KtxInputAdapter {

            override fun keyUp(keycode: Int): Boolean {

                when (keycode) {
                    Keys.SPACE -> simStarted = !simStarted
                    Keys.H -> simulation.hideWalkers = !simulation.hideWalkers
                    Keys.D -> {
                        fps.isRendered = !fps.isRendered; simulation.isDebug = !simulation.isDebug
                    }

                    Keys.S -> {
                        if (Gdx.app.type == Application.ApplicationType.Desktop) {
                            screenshot()
                        }
                    }
                }
                return true
            }

            override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                simStarted = !simStarted
                return true
            }
        }
        simulation = Simulation(
            gameViewport.worldWidth,
            gameViewport.worldHeight,
            RandomWalkStrategy(gameViewport.worldWidth, gameViewport.worldHeight),
            RandomInitialSeedStrategy(),
            RandomAllOverSpawnStrategy()
        )
    }

    override fun render(delta: Float) {
        super.render(delta)
        clearScreen(red = 0.0f, green = 0.0f, blue = 0.0f)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        if (simStarted) {
            simulation.update()
        }

        fps.update(delta)

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()

        gameViewport.apply()

        shape.use(ShapeRenderer.ShapeType.Filled, gameViewport.camera.combined) {
            simulation.draw(it)
        }

//        vector.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
//        gameViewport.camera.unproject(vector)
//        Gdx.graphics.setTitle("DEBUG - X: ${vector.x} Y: ${vector.y}")

        uiViewport.apply()
        batch.use(uiViewport.camera.combined) {
            fps.render(it, simulation.walkerCount(), simulation.treeCount(), 0f, uiViewport.worldHeight)
        }
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }

    override fun dispose() {
        batch.disposeSafely()
        fps.disposeSafely()
        shape.disposeSafely()
    }

    private fun screenshot() {
        val pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.backBufferWidth, Gdx.graphics.backBufferHeight)
        val pixels = pixmap.getPixels();

        // This loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        val size = Gdx.graphics.backBufferWidth * Gdx.graphics.backBufferHeight * 4
        for (i in 3 until size step 4) {
            pixels.put(i, 255.toByte())
        }

        PixmapIO.writePNG(
            FileHandle("mypixmap${(Math.random() * 10000).toInt()}.png"),
            pixmap,
            Deflater.DEFAULT_COMPRESSION,
            true
        )
        pixmap.dispose()
    }
}