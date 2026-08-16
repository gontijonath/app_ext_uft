plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "br.edu.uft.estersabino"
    compileSdk = 36

    defaultConfig {
        applicationId = "br.edu.uft.estersabino"
        // ARCore exige API 24+
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "0.1-prototipo"
    }

    // Assinatura do APK de distribuição.
    //
    // Para ativar: gere um keystore (Android Studio → Build → Generate Signed
    // App Bundle / APK) e coloque os quatro valores abaixo no arquivo
    // `~/.gradle/gradle.properties` — nunca neste repositório, e nunca no
    // gradle.properties do projeto, que vai para o controle de versão.
    //
    //   ESTER_STORE_FILE=/caminho/para/ester-sabino.jks
    //   ESTER_STORE_PASSWORD=...
    //   ESTER_KEY_ALIAS=...
    //   ESTER_KEY_PASSWORD=...
    //
    // GUARDE O KEYSTORE. Sem ele não é possível publicar uma atualização como
    // o mesmo app: os visitantes teriam que desinstalar e reinstalar.
    val temKeystore = project.hasProperty("ESTER_STORE_FILE")
    if (temKeystore) {
        signingConfigs {
            create("distribuicao") {
                storeFile = file(project.property("ESTER_STORE_FILE") as String)
                storePassword = project.property("ESTER_STORE_PASSWORD") as String
                keyAlias = project.property("ESTER_KEY_ALIAS") as String
                keyPassword = project.property("ESTER_KEY_PASSWORD") as String
            }
        }
    }

    buildTypes {
        release {
            if (temKeystore) {
                signingConfig = signingConfigs.getByName("distribuicao")
            }

            // Protótipo distribuído por APK direto: mantemos desativado para
            // facilitar diagnóstico caso algo quebre no dia do evento.
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            ndk {
                // Só ARM no APK de distribuição. As bibliotecas nativas do
                // Filament e do ARCore para x86/x86_64 pesam ~14 MB e só
                // servem a emuladores — onde a RA não funciona de qualquer
                // forma. O build de debug segue universal, para quem quiser
                // abrir as telas num emulador.
                abiFilters += listOf("arm64-v8a", "armeabi-v7a")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        // O .glb já é binário comprimido; recomprimir só atrasa a leitura.
        jniLibs.useLegacyPackaging = false
    }

    androidResources {
        noCompress += "glb"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.ui.text.google.fonts)
    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.androidx.navigation.compose)

    // Fallback de câmera para aparelhos sem ARCore
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    // Realidade aumentada (traz ARCore + Filament)
    implementation(libs.sceneview.ar)
}
