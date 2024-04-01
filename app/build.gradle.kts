import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("dev.shreyaspatil.compose-compiler-report-generator") version "1.2.0"
}

android {
    namespace = "com.example.sample"
    compileSdk = Sdk.compile

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
        minSdk = Sdk.min
        targetSdk = Sdk.target
        versionCode = AppVersion.code
        versionName = AppVersion.str

        resValue("integer", "app_version_code", AppVersion.code.toString())

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
            resValue("string","app_version_name", AppVersion.str + versionNameSuffix)
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
            resValue("string","app_version_name", AppVersion.str)
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
            resValue("string","app_version_name", AppVersion.str + versionNameSuffix)
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
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Libraries.androidxCore)
    implementation(Libraries.lifecycleRuntime)
    implementation(Libraries.activityCompose)

    implementation(Libraries.appCompat)

    implementation(Libraries.splashScreen)

    implementation(platform(Libraries.composeBom))
    implementation(Libraries.composeUi)
    implementation(Libraries.composeUiGraphics)
    implementation(Libraries.composeUiToolingPreview)
    implementation(Libraries.material3)
    debugImplementation(Libraries.composeUiTooling)
    debugImplementation(Libraries.composeUiTestManifest)

    implementation(Libraries.navigationUi)
    implementation(Libraries.navigationFragment)

    debugImplementation(Libraries.leakcanary)

    implementation(Libraries.coilCompose)

    implementation(Libraries.hilt)
    kapt(AnnotationProcessor.hiltCompiler)

    implementation(Libraries.timber)

    testImplementation(Libraries.junit)
}