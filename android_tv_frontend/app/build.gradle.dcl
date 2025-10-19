androidApplication {
    namespace = "com.smarthome.tv"

    dependencies {
        // Core + Activity
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.activity:activity-compose:1.8.0")

        // Compose UI stack (pinned to 1.5.4 for compatibility with Kotlin compiler in this template)
        implementation("androidx.compose.ui:ui:1.5.4")
        implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
        implementation("androidx.compose.material3:material3:1.1.2")
        implementation("androidx.compose.foundation:foundation:1.5.4")
        implementation("androidx.compose.runtime:runtime:1.5.4")
        implementation("androidx.compose.runtime:runtime-saveable:1.5.4")
        implementation("androidx.compose.animation:animation:1.5.4")
        implementation("androidx.compose.ui:ui-graphics:1.5.4")

        // TV Compose libs (compatible with 1.6.x compose)
        implementation("androidx.tv:tv-foundation:1.0.0-alpha10")
        implementation("androidx.tv:tv-material:1.0.0-alpha10")

        // Lifecycle/ViewModel
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

        // Material Icons
        implementation("androidx.compose.material:material-icons-extended:1.5.4")

        // (Removed) Google Fonts provider; using fallback FontFamily
    }
}
