plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

val versionMajor = 0
val versionMinor = 0
val versionPatch = 2

android {
    namespace = "com.xcaret.loyaltyreps"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.xcaret.loyaltyreps"
        minSdk = 28
        targetSdk = 35
        versionCode =  versionMajor * 10000 + versionMinor * 100+ versionPatch
        versionName =  "${versionMajor}.${versionMinor}.${versionPatch}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField ("String", "XCARETAPI", "\"https://api.loyaltyreps.com/\"")
            buildConfigField("String", "DBCore", "\"https://agencia-viajes-2e9f0-default-rtdb.firebaseio.com\"")
            buildConfigField ("String", "PUNK_API_URL", "\"https://app.loyaltyreps.com/api/v1/\"")
            buildConfigField ("String", "EVENT_BRITE_URL", "\"https://www.eventbriteapi.com/v3/\"")
        }
        debug{
            isMinifyEnabled =  false
            buildConfigField ("String", "XCARETAPI", "\"https://api.loyaltyreps.com/\"")
            buildConfigField ("String", "DBCore", "\"https://agencia-viajes-2e9f0-default-rtdb.firebaseio.com\"")
            buildConfigField ("String", "PUNK_API_URL", "\"https://app.loyaltyreps.com/api/v1/\"")
            buildConfigField ("String", "EVENT_BRITE_URL", "\"https://www.eventbriteapi.com/v3/\"")
            versionNameSuffix = "-dev"
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
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //Lottie
    implementation(libs.lottie)
    implementation(libs.firebase.database.ktx)


    //retrofit
    implementation(libs.square.retrofit.retrofit)
    implementation(libs.square.retrofit.retrofit.gson.converter)
    implementation(libs.square.retrofit.retrofit.adapter.java)
    implementation(libs.square.retrofit.retrofit.converter.scalars)

    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences)
//    implementation(libs.androidx.room.ktx)

    //lifecicle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    //glide
    implementation(libs.glide)

    //ROOM
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.firebase.database)
    implementation(libs.androidx.gridlayout)
    implementation(libs.androidx.runner)

    ksp(libs.androidx.room.compiler)

    //navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    //exoplayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.ui)
    implementation(libs.media3.exoplayer.dash)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}