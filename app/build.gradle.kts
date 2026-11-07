plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.start.clienttest"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.start.clienttest"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        externalNativeBuild {
            ndkBuild {
                abiFilters.addAll(listOf("armeabi-v7a"))
                arguments.add("APP_STL:=gnustl_shared")
            }
        }
        ndk {
            abiFilters.addAll(listOf("armeabi-v7a"))
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    useLibrary("org.apache.http.legacy")
    externalNativeBuild {
        ndkBuild {
            path = file("src/main/jni/Android.mk")
        }
    }
    ndkVersion = "16.1.4479499"
    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}