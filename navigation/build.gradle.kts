plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ta.ittpizen.navigation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":ui"))

    implementation(project(":feature-splash-screen"))
    implementation(project(":feature-onboarding-screen"))
    implementation(project(":feature-auth"))
    implementation(project(":feature-home"))
    implementation(project(":feature-connection"))
    implementation(project(":feature-chat"))
    implementation(project(":feature-post"))
    implementation(project(":feature-job"))
    implementation(project(":feature-profile"))
    implementation(project(":feature-notification"))
    implementation(project(":feature-photo-detail"))

    implementation(project(":feature-main"))

    api("androidx.navigation:navigation-compose:2.7.6")
    api("com.google.accompanist:accompanist-navigation-material:0.33.2-alpha")

}