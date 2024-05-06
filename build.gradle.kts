buildscript {
    dependencies {
        classpath(Plugins.hilt)
        classpath(Plugins.navSafeArgs)
    }
}

plugins {
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin apply false
}