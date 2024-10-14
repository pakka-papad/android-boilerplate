import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("dev.shreyaspatil.compose-compiler-report-generator") version "1.4.0"
}

android {
    namespace = "com.example.sample"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    val major = libs.versions.app.major.get().toInt()
    val minor = libs.versions.app.minor.get().toInt()
    val patch = libs.versions.app.patch.get().toInt()
    val verCode = 10000 * major + 100 * minor + patch
    val verName = "$major.$minor.$patch"

    signingConfigs {
        create("release") {
            storeFile = gradleLocalProperties(rootDir)["RELEASE_STORE_FILE"]?.let { file(it) }
            storePassword = gradleLocalProperties(rootDir)["RELEASE_STORE_PASSWORD"] as String
            keyAlias = gradleLocalProperties(rootDir)["RELEASE_KEY_ALIAS"] as String
            keyPassword = gradleLocalProperties(rootDir)["RELEASE_KEY_PASSWORD"] as String
        }
        create("staging"){
            storeFile = gradleLocalProperties(rootDir)["STAGING_STORE_FILE"]?.let { file(it) }
            storePassword = gradleLocalProperties(rootDir)["STAGING_STORE_PASSWORD"] as String
            keyAlias = gradleLocalProperties(rootDir)["STAGING_KEY_ALIAS"] as String
            keyPassword = gradleLocalProperties(rootDir)["STAGING_KEY_PASSWORD"] as String
        }
    }

    defaultConfig {
        applicationId = "com.example.sample"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = verCode
        versionName = verName

        resValue("integer", "app_version_code", versionCode?.toString()!!)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
            resValue("string","app_version_name", verName + versionNameSuffix)
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
            resValue("string","app_version_name", verName)
        }
        create("staging") {
            isMinifyEnabled = true
            isShrinkResources = true
            versionNameSuffix = "-staging"
            applicationIdSuffix = ".staging"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("staging")
            resValue("string","app_version_name", verName + versionNameSuffix)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.lifecycle.runtime)
    implementation(libs.activity.compose)

    implementation(libs.app.compat)

    implementation(libs.splash.screen)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.perview)
    implementation(libs.material3)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)

    //debugImplementation(libs.leakcanary)

    implementation(libs.coil.compose)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)

    testImplementation(libs.junit)
}