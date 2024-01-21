import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kover)
    alias(libs.plugins.detekt)
}

android {
    namespace = "net.spaceflightnews"
    compileSdk = 34

    defaultConfig {
        applicationId = "net.spaceflightnews"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "BASE_URL", "\"https://api.spaceflightnewsapi.net/v4/\"")
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
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,gradle/incremental.annotation.processors}"
        }
    }
}

koverReport {
    filters {
        excludes {
            classes(
                // generated files
                "hilt_*",
                "*Hilt*",
                "*.BuildConfig",
                "*.ComposableSingletons\$*",
                "*.codegen.*",
                "*_Factory*",
                // exclude dependency injection modules
                "*.di.*",
                // exclude UI
                "*.**Activity",
                "*.**App",
                "*.theme.*",
            )
            annotatedBy(
                // excludes all composable functions
                "androidx.compose.runtime.Composable",
            )
        }
    }
    androidReports("debug") {
        html {
            onCheck =
                false // set to true to run koverHtmlReportDebug task during the execution of the check task (if it exists) of the current project
            setReportDir(layout.buildDirectory.dir("coverageReport")) // change report directory
        }
    }
}

detekt {
    parallel = true
    config.setFrom(files("$rootDir/detekt-config.yml"))
    baseline = file("detekt-baseline.xml")
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}

dependencies {
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.paging.common)
    implementation(libs.paging.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.paging.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.testing)
    testImplementation(libs.turbine)
    testImplementation(platform(libs.compose.bom))
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.rules.libraries)
}
