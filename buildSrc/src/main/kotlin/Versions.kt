object Versions {
    const val kotlin = "1.9.0"

    const val androidxCore = "1.12.0"
    const val lifecycleRuntime = "2.7.0"
    const val activityCompose = "1.8.2"

    const val splashScreen = "1.0.1"

    const val composeCompiler = "1.5.1"
    const val composeBom = "2023.08.00"

    const val leakcanary = "2.9.1"

    const val coilCompose = "2.2.2"

    const val hilt = "2.38.1"

    const val timber = "5.0.1"

    const val junit = "4.13.2"
}

object AppVersion {
    private const val major = 1
    private const val minor = 0
    private const val patch = 0
    const val code = major*10000 + minor*100 + patch
    const val str = "$major.$minor.$patch"
}