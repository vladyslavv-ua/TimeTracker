import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
//import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

//import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")

    }
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop"){
        compilerOptions{
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.android.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)

        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.material.icons.core)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.compose.navigation)
            implementation(libs.compose.viewmodel)
            implementation(libs.kotlinx.serialization)

            implementation(libs.androidx.lifecycle.runtime.compose)


            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.kotlinx.datetime)

            implementation(libs.room.runtime)
            implementation(libs.room.gradleplugin)
            implementation(libs.sqlite.bundled)

            implementation(libs.koalaplot)


            implementation(libs.dataframe.jdbc)
            implementation(libs.sqlite.jdbc)

        }
        desktopMain.dependencies {
            implementation(compose.desktop.linux_x64)
            implementation(compose.desktop.macos_x64)
            implementation(compose.desktop.macos_arm64)
            implementation(compose.desktop.windows_x64)
            implementation(libs.koin.compose.jvm)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "io.vladyslavv_ua.time_tracker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "io.vladyslavv_ua.time_tracker"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("jniLibs")
        }
    }


    packaging {
        resources {
            pickFirsts += listOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/ASL-2.0.txt",
                "META-INF/LICENSE.md",
                "META-INF/NOTICE.md",
                "META-INF/LGPL-3.0.txt",
            )
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += listOf(
                "META-INF/kotlin-jupyter-libraries/libraries.json",
                "META-INF/{INDEX.LIST,DEPENDENCIES}",
                "{draftv3,draftv4}/schema",
                "arrow-git.properties",
            )
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        getByName("debug") {
            isMinifyEnabled = false
            resValue("string", "app_name", "Debug Time tracker")

        }
    }
    compileOptions {
        //        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
        //        coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.1.5")

    }
}

compose.desktop {
    application {
        mainClass = "io.vladyslavv_ua.time_tracker.MainKt"
        buildTypes.release.proguard {
            isEnabled.set(false)
            obfuscate.set(false)
            configurationFiles.from(project.file("compose-desktop-proguard.pro"))
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.vladyslavv_ua.time_tracker"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    add("kspDesktop", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
    add("kspCommonMainMetadata", libs.room.compiler)

}

//tasks.withType<KotlinJvmCompile>().configureEach {
//    compilerOptions {
//        jvmTarget.set(JvmTarget.JVM_11)
//        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
//    }
//}



configurations.forEach { println(it.name) }
