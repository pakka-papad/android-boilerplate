plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
}

android {
    namespace = "com.example.sample"
    compileSdk = Sdk.compile

    defaultConfig {
        applicationId = "com.example.sample"
        minSdk = Sdk.min
        targetSdk = Sdk.target
        versionCode = AppVersion.code
        versionName = AppVersion.str

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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