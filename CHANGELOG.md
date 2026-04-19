# Changelog

All notable changes to this project will be documented in this file.

---

## [0.1.0] - Initial Android UI Setup

### Added
- Android project structure inside repo
- MainActivity with Compose setup
- HomeScreen with mock data
- SongCard UI component

### UI Enhancements
- PlayerScreen with controls (play, next, previous)
- MiniPlayer (bottom bar)
- Improved HomeScreen (V2) with MiniPlayer integration

### Architecture
- Clean architecture folders (data, domain, ui, player)
- Domain model: Song
- API interface skeleton (SaavnApi)
- PlayerController (ExoPlayer setup)

### Added
- Android build setups: `build.gradle.kts`, `settings.gradle.kts`
- Android Manifest file and essential resource folders + strings.xml
- `gradle-wrapper.properties` configuration with Gradle 8.1.1
- Backup rules and data extraction rules XML files

### Changes
- Fixed compilation issues by adding missing dependencies (`retrofit`, `media3-exoplayer`, `navigation-compose`, `coil-compose`).
- Removed duplicate files `HomeScreenV2.kt` and `HomeScreenV3.kt` that were causing `SongCard` `Conflicting overloads` errors.
- Fixed `AppNavigation.kt` to use `HomeScreen()` correctly.
- Added missing resources required for Android building (`strings.xml`, `themes.xml`, and extraction rules).
- Ran and successfully deployed the app via `adb shell am start`.
- UI-first development approach (mock data)
- API integration pending

---
