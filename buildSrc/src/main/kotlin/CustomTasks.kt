import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.bundling.Tar
import org.gradle.api.tasks.bundling.Zip
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import java.util.*

fun Project.registerDesktopTasks(mainClass: String) {

    tasks.getByName<JavaExec>("run") {
        workingDir = rootProject.file("assets")
        setIgnoreExitValue(true)

        // This next line could be needed to run LWJGL3 Java apps on macOS, but StartupHelper should make it unnecessary.
        // val os = System.getProperty("os.name").lowercase(Locale.getDefault())
        // if (os.contains("mac")) jvmArgs("-XstartOnFirstThread")
        // If you encounter issues with the 'lwjgl3:run' task on macOS specifically, try uncommenting the above line, and
        // regardless, please report it via the gdx-liftoff issue tracker or just mention it on the libGDX Discord.
    }

    tasks.getByName<Tar>("distTar") {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    tasks.getByName<Zip>("distZip") {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    tasks.getByName<Jar>("jar") {
        // sets the name of the .jar file this produces to the name of the game or app, with the version after.
        archiveFileName.set("${project.name}-${project.version}.jar")
        // the duplicatesStrategy matters starting in Gradle 7.0; this setting works.
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val runtimeClasspath = configurations.getByName("runtimeClasspath")
        dependsOn(runtimeClasspath)

//        val destDir = file(project.layout.buildDirectory.asFile.get().absolutePath + File.separator + "lib")
//        // using 'lib' instead of the default 'libs' appears to be needed by jpackageimage.
//        destinationDirectory.set(destDir)

        from({
            runtimeClasspath.map { file ->
                if (file.isDirectory) file else zipTree(file)
            }
        })

        // these "exclude" lines remove some unnecessary duplicate files in the output JAR.
        exclude("META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")

        dependencies {
            exclude("META-INF/INDEX.LIST", "META-INF/maven/**")
        }
        // setting the manifest makes the JAR runnable.
        manifest {
            attributes["Main-Class"] = mainClass
        }
        // this last step may help on some OSes that need extra instruction to make runnable JARs.
        doLast {
            file(archiveFile).setExecutable(true, false)
        }
    }

    // Builds a JAR that only includes the files needed to run on macOS, not Windows or Linux.
    // The file size for a Mac-only JAR is about 7MB smaller than a cross-platform JAR.
    tasks.register("jarMac") {
        val jar = tasks.getByName<Jar>("jar")
        dependsOn(jar)
        group = "build"
        description = "Builds a Mac-only JAR"

        jar.archiveFileName.set("${project.name}-${project.version}-mac.jar")

        jar.exclude(
            "windows/x86/**",
            "windows/x64/**",
            "linux/arm32/**",
            "linux/arm64/**",
            "linux/x64/**",
            "**/*.dll",
            "**/*.so",
            "META-INF/INDEX.LIST",
            "META-INF/*.SF",
            "META-INF/*.DSA",
            "META-INF/*.RSA",
        )
        dependencies {
            jar.exclude(
                "windows/x86/**",
                "windows/x64/**",
                "linux/arm32/**",
                "linux/arm64/**",
                "linux/x64/**",
                "META-INF/INDEX.LIST",
                "META-INF/maven/**"
            )
        }
    }

    // Builds a JAR that only includes the files needed to run on Linux, not Windows or macOS.
    // The file size for a Linux-only JAR is about 5MB smaller than a cross-platform JAR.
    tasks.register("jarLinux") {
        val jar = tasks.getByName<Jar>("jar")
        dependsOn(jar)
        group = "build"
        description = "Builds a Linux-only JAR"

        jar.archiveFileName.set("${project.name}-${project.version}-linux.jar")

        jar.excludes.apply {
            add("windows/x86/**")
            add("windows/x64/**")
            add("macos/arm64/**")
            add("macos/x64/**")
            add("**/*.dll")
            add("**/*.dylib")
            add("META-INF/INDEX.LIST")
            add("META-INF/*.SF")
            add("META-INF/*.DSA")
            add("META-INF/*.RSA")
        }

        dependencies {
            jar.exclude(
                "windows/x86/**",
                "windows/x64/**",
                "macos/arm64/**",
                "macos/x64/**",
                "META-INF/INDEX.LIST",
                "META-INF/maven/**"
            )
        }
    }

    // Builds a JAR that only includes the files needed to run on Windows, not Linux or macOS.
    // The file size for a Windows-only JAR is about 6MB smaller than a cross-platform JAR.
    tasks.register("jarWin") {
        val jar = tasks.getByName<Jar>("jar")
        dependsOn(jar)
        group = "build"
        description = "Builds a Windows-only JAR"

        jar.archiveFileName.set("${project.name}-${project.version}-win.jar")

        jar.excludes.apply {
            add("macos/arm64/**")
            add("macos/x64/**")
            add("linux/arm32/**")
            add("linux/arm64/**")
            add("linux/x64/**")
            add("**/*.dylib")
            add("**/*.so")
            add("META-INF/INDEX.LIST")
            add("META-INF/*.SF")
            add("META-INF/*.DSA")
            add("META-INF/*.RSA")
        }

        dependencies {
            jar.exclude(
                "macos/arm64/**",
                "macos/x64/**",
                "linux/arm32/**",
                "linux/arm64/**",
                "linux/x64/**",
                "META-INF/INDEX.LIST",
                "META-INF/maven/**"
            )
        }
    }
}

fun Project.registerAndroidTasks() {

    tasks.register<Copy>("copyAndroidNatives") {

        file("libs/armeabi-v7a/").mkdirs()
        file("libs/arm64-v8a/").mkdirs()
        file("libs/x86_64/").mkdirs()
        file("libs/x86/").mkdirs()

        configurations.named("natives").orNull?.copy()?.files?.forEach { jar ->
            val outputDir = when {
                jar.name.endsWith("natives-armeabi-v7a.jar") -> {
                    file("libs/armeabi-v7a")
                }

                jar.name.endsWith("natives-arm64-v8a.jar") -> {
                    file("libs/arm64-v8a")
                }

                jar.name.endsWith("natives-x86_64.jar") -> {
                    file("libs/x86_64")
                }

                jar.name.endsWith("natives-x86.jar") -> {
                    file("libs/x86")
                }

                else -> {
                    null
                }
            }

            if (outputDir != null) {
                copy {
                    from(zipTree(jar))
                    into(outputDir)
                    include("*.so")
                }
            }
        }
    }

    tasks.matching { it.name.contains("merge") && it.name.contains("JniLibFolders") }
        .configureEach {
            dependsOn("copyAndroidNatives")
        }

    tasks.register<Exec>("run") {

        val localProperties = project.file("../local.properties")
        val path: String? = if (localProperties.exists()) {
            val properties = Properties()
            properties.load(localProperties.inputStream())
            properties.getProperty("sdk.dir") ?: System.getenv("ANDROID_SDK_ROOT")
        } else {
            System.getenv("ANDROID_SDK_ROOT")
        }

        val adb = "$path/platform-tools/adb"
        commandLine(
            adb,
            "shell",
            "am",
            "start",
            "-n",
            "com.badlogic.invaders/com.badlogic.invaders.android.AndroidLauncher"
        )
    }
}