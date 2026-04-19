# Changelog

All notable changes to this project will be documented in this file.

---

## [0.5.0] - 2026-04-19

### Added
- Full-player More menu sheet matching the requested Saavn-style layout with quick actions and detailed playback options.
- Dedicated player menu strings for labels, subtitles, and accessibility content descriptions.
- New player menu color tokens for sheet background, volume block, quick-action tiles, and list items.

### Changed
- Full-player More button now opens the new menu sheet instead of remaining a static icon.
- Full-player More menu now opens at half height on first tap and expands to full height when the user scrolls up.

## [0.4.0] - 2026-04-19

### Added
- Profile popup menu opened from the app bar profile icon with options matching the requested design: account row, token action, more content toggle, auto sync toggle, integrations, settings, and new version notice.

### Changed
- Removed `Settings` from bottom navigation.
- Moved access to `Settings` into the profile popup menu on Home and Library top bars.

### Fixed
- Preserved existing `settings` route access through profile menu navigation instead of bottom-tab navigation.

## [0.3.0] - 2026-04-19

### Added
- Full-screen player UI that matches the requested Saavn-style mock with centered Now Playing heading, artwork card, and bottom action rail.
- New player-specific color tokens for gradient background, control surfaces, and progress styling.
- Accessibility content descriptions and text resources for all major player controls.

### Changed
- Replaced the placeholder player layout with a stylized Compose implementation featuring action buttons, custom progress section, and transport controls.

## [0.2.0] - 2026-04-19

### Added
- Library screen UI matching the requested Saavn-style layout with a Home-like header titled "Library".
- Category chips for Playlists, Songs, Albums, and Artists.
- Sort popup options (Date added, Name, Date updated) and grid/list view toggle in Library.
- Library collection shortcuts: Liked, Downloaded, My top 50, Cached, and Uploaded.
- New Library color tokens and string resources for UI labels and accessibility descriptions.

### Changed
- Shared `TopBar` now accepts a dynamic title and uses string resources for accessibility content descriptions.

### Fixed
- Replaced the placeholder Library screen text-only view with a complete, scrollable layout.

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
