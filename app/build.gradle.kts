plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtoolsKsp)

//    alias(libs.plugins.kotlin.ksp)
//    alias(libs.plugins.kotlin.kapt)
}


android {
    namespace = "com.example.criminalintent"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.criminalintent"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources.excludes.add("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.identity.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.room.compiler)
    ksp(libs.androidx.room.room.compiler)
//    implementation(libs.lifecycle.livedata.ktx)

    implementation(libs.lifecycle.livedata)
}