package io.github.thanosfisherman.dla.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.PixmapIO
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.github.thanosfisherman.dla.*
import io.github.thanosfisherman.dla.snowflake.Snowflake
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.circle
import ktx.graphics.rectLine
import ktx.graphics.use
import ktx.math.vec2
import ktx.math.vec3
import java.util.zip.Deflater


class SecondScreen : KtxScreen {
    private val gameViewport = ScreenViewport()
    private val uiViewport = ScreenViewport()
    private val batch = SpriteBatch()
    private val shape = ShapeRenderer()
    private val fps = FrameRate()

    private val vectorMouse = vec3()

    private val vector30: Vector2 = vec2(1f, 0f)

    private var simStarted = false
    private lateinit var snowflake: Snowflake

//    private val vector = vec3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())


    override fun show() {

        vector30.setAngleDeg(30f)
        vector30.setLength(1000f)

        snowflake = Snowflake(gameViewport.worldWidth, gameViewport.worldHeight)
        gameViewport.apply()
    }

    override fun render(delta: Float) {
        super.render(delta)
        clearScreen(red = 0.0f, green = 0.0f, blue = 0.0f)


        if (simStarted) {

        }

        fps.update(delta)

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()

        gameViewport.apply()

//        vectorPart.x -= 0.8f
//        vectorPart.y += Juniper.random.nextFloat(-10f, 10f)
//        val angle = vectorPart.angleRad()
//        vectorPart.constrainAngleRad(angle, 0f, MathUtils.PI / 6)

        snowflake.update()
        shape.use(ShapeRenderer.ShapeType.Filled, gameViewport.camera.combined) {
            it.rectLine(
                0f,
                0f,
                gameViewport.worldWidth,
                0f,
                5f,
                Color.RED,
                Color.RED,
            )

            it.rectLine(
                0f,
                0f,
                vector30.x,
                vector30.y,
                5f,
                Color.GREEN,
                Color.GREEN,
            )

            snowflake.draw(it)
//            val newpart = screenCenter.cpy().add(vectorPart)
//            it.circle(newpart.x, newpart.y, 8f)

        }

        vectorMouse.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
        gameViewport.camera.unproject(vectorMouse)
        Gdx.graphics.setTitle("DEBUG - X: ${vectorMouse.x} Y: ${vectorMouse.y}")

        uiViewport.apply()
        batch.use(uiViewport.camera.combined) {
            fps.render(it, 0, 0, 0f, uiViewport.worldHeight)
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