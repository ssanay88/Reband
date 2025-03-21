plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "2.1.0"
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.project.reband"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.reband"
        minSdk = 26
        targetSdk = 34
        versionCode = 7
        versionName = "1.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
        dataBinding = true
        buildConfig = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(files("libs/oauth-5.10.0.aar"))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.v2.user)    // 카카오 로그인
    implementation(files("libs/oauth-5.10.0.aar"))    // 네이버 로그인
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    implementation("androidx.fragment:fragment-ktx:1.8.4")    // 프래그먼트 ktx 라이브러리 추가
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // DataStore
    implementation("androidx.datastore:datastore-core:1.1.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    // sharedPreference
    implementation("androidx.preference:preference:1.2.1")
    // Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    // lottie
    implementation("com.airbnb.android:lottie:6.1.0")
    // firebase
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")

    // compose
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview")
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    debugImplementation(libs.androidx.ui.test.manifest)


}