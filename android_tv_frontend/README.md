# SmartHome Android TV Frontend

This is a native Android TV app built with Kotlin and Jetpack Compose (TV-friendly) that replicates the provided design:
- Left icon-only sidebar: Home, Activity, Settings.
- Home tab shows "Devices" header with a total count badge and a grid of device cards.
- Device cards are TV-focusable, change background based on ON/OFF, and toggle with DPAD_CENTER.
- Activity and Settings tabs show simple headings.

## Tech
- Jetpack Compose Material 3
- TV focus support via Compose focus APIs
- Google Font: Figtree (via Google Play services font provider)
- Material Icons (Filled)

## Remote Navigation & Controls
- D-Pad Up/Down/Left/Right: Move focus between sidebar icons and device cards.
- DPAD_CENTER (OK): 
  - On sidebar item: change tab.
  - On device card: toggle ON/OFF state.
- BACK: default Android behavior.

## Theming
Ocean Professional:
- Primary: #2563EB
- Secondary/Success: #F59E0B
- Error: #EF4444
- Background: #f9fafb (dark backgrounds for TV are supported)
- Surface: #ffffff
- Text: #111827

Figtree is applied globally through Compose Typography.

## Build and Run
From `android_tv_frontend`:
```bash
./gradlew :app:installDebug
```
Launch on an Android TV device/emulator; app appears in the TV launcher.

## Notes
- State is in-memory via HomeViewModel; no persistence.
- No WebView usage; only native Compose.
