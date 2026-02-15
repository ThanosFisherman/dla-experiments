@file:JvmName("TeaVMBuilder")

package io.github.thanosfisherman.dla.teavm


import com.github.xpenatan.gdx.teavm.backends.shared.config.AssetFileHandle
import com.github.xpenatan.gdx.teavm.backends.shared.config.compiler.TeaCompiler
import com.github.xpenatan.gdx.teavm.backends.web.config.backend.WebBackend
import org.teavm.tooling.TeaVMSourceFilePolicy
import org.teavm.vm.TeaVMOptimizationLevel
import java.io.File

/** Builds the TeaVM/HTML application. */
object TeaVMBuilder {
    private const val DEBUG = true

    @JvmStatic
    fun main(arguments: Array<String>) {

        val webBackend = WebBackend().apply {
            startJettyAfterBuild = true
            htmlTitle = "Thanos Fisherman"
            htmlWidth = 900
            htmlHeight = 600
            isWebAssembly = false
            webappFolderName = "webapp"
            jettyPort = 8080
        }

        TeaCompiler(webBackend).apply {
            addAssets(AssetFileHandle("../assets"))
            setOptimizationLevel(TeaVMOptimizationLevel.SIMPLE)
            setMainClass(TeaVMLauncher::class.qualifiedName)
            setObfuscated(false)
            setDebugInformationGenerated(true)
            setSourceMapsFileGenerated(true)
            setSourceFilePolicy(TeaVMSourceFilePolicy.LINK_LOCAL_FILES)
            build(File("dist"))
        }
    }
}