@file:JvmName("DesktopLauncher")

package io.github.thanosfisherman.dla.desktop

import com.github.dgzt.gdx.lwjgl3.Lwjgl3ApplicationConfiguration
import com.github.dgzt.gdx.lwjgl3.Lwjgl3VulkanApplication
import io.github.thanosfisherman.dla.Game
import org.lwjgl.system.Configuration

fun main() {
    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
        return

    val config =
        Lwjgl3ApplicationConfiguration().apply {
            val displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode()
            val width = displayMode.width * 0.75f
            val height = width / 16f * 9f
            setWindowedMode(width.toInt(),height.toInt())
            //setFullscreenMode(displayMode)
            setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
            setTitle("Diffusion-limited aggregation experiments!")
            useVsync(true)
            //// Limits FPS to the refresh rate of the currently active monitor.
            setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate)
            //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
            //// useful for testing performance, but can also be very stressful to some hardware.
            //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
            setBackBufferConfig(8, 8, 8, 8, 24, 8, 4)
            //setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.ANGLE_GLES32, 0, 0)

        }

    Lwjgl3VulkanApplication(Game(), config)

}