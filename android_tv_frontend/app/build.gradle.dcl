androidApplication {
    namespace = "com.smarthome.tv"

    dependencies {
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.fragment:fragment-ktx:1.8.5")
        implementation("androidx.cardview:cardview:1.0.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.leanback:leanback:1.2.0-alpha04")

        // Material3 and Compose UI (icons usage and potential previews)
        implementation("androidx.compose.material3:material3:1.3.0")
        implementation("androidx.compose.material:material-icons-extended:1.7.3")
        implementation("androidx.activity:activity-compose:1.9.2")
        implementation("androidx.navigation:navigation-compose:2.8.2")
        implementation("androidx.compose.ui:ui:1.7.3")
        implementation("androidx.compose.ui:ui-tooling-preview:1.7.3")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
    }
}
