@file:JvmName("TeaVMLauncher")

package io.github.thanosfisherman.dla.teavm

import com.github.xpenatan.gdx.teavm.backends.web.WebApplication
import com.github.xpenatan.gdx.teavm.backends.web.WebApplicationConfiguration
import io.github.thanosfisherman.dla.Game


/**
 * Launches the TeaVM/HTML application.
 * <br></br>
 * It may be important to note that if the TeaVM page is loaded from a URL with parameters,
 * that is, with a '?' sign after ".html" or some other file extension, then loading any
 * assets might not work right now. This is especially true when loading via IntelliJ IDEA's
 * built-in web server, which will default to adding on URL parameters that can be removed.
 */
object TeaVMLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val config = WebApplicationConfiguration("canvas").apply {
            usePhysicalPixels = false
            antialiasing = true
            showDownloadLogs = false
            //preloadListener = AssetInstance.getLoaderInstance().loadScript("freetype.js")
            // change these to both 0 to use all available space, or both -1 for the canvas size.
            width = 0
            height = 0
        }
        WebApplication(Game.create(), config)
    }
}
