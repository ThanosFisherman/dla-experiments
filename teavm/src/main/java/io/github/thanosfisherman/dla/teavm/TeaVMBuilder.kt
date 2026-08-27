@file:JvmName("TeaVMBuilder")

package io.github.thanosfisherman.dla.teavm



/** Builds the TeaVM/HTML application. */
//object TeaVMBuilder {
//    private const val DEBUG = true
//
//    @JvmStatic
//    fun main(arguments: Array<String>) {
//
//        val webBackend = WebBackend().apply {
//            startJettyAfterBuild = true
//            htmlTitle = "Thanos Fisherman"
//            htmlWidth = 1920
//            htmlHeight = 1080
//            isWebAssembly = true
//            //webappFolderName = "webapp"
//            jettyPort = 8080
//        }
//
//        TeaCompiler(webBackend).apply {
//            addAssets(AssetFileHandle("../assets"))
//            setOptimizationLevel(TeaVMOptimizationLevel.FULL)
//            setMainClass(TeaVMLauncher::class.qualifiedName)
//            setObfuscated(true)
//            setDebugInformationGenerated(false)
//            setSourceMapsFileGenerated(false)
//            setSourceFilePolicy(TeaVMSourceFilePolicy.LINK_LOCAL_FILES)
//            build(File("../dist"))
//        }
//    }
//}