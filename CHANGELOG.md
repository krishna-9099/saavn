# Changelog

All notable changes to this project will be documented in this file.

---

## [0.7.1] - 2026-04-19

### Fixed
- Reduced oversized decorative typography on compact home surfaces after the Metrolist theme port.
- Restored default font families for compact body, title, and label roles so card captions, mini-player metadata, and bottom navigation labels no longer render too large.
- Tightened Home header, section heading, and artwork-card text sizing to better match the intended Saavn/Metrolist proportions on phone screens.

## [0.7.0] - 2026-04-19

### Added
- Added artwork palette extraction utilities and a shared player palette model to drive Metrolist-style gradients in the mini player and full player.
- Added a new Metrolist-inspired full player shell with gradient background, marquee metadata, rounded artwork stage, refreshed transport controls, and updated action chips.
- Added a new pill-style mini player with artwork progress ring, gradient background, and theme-aware quick actions.

### Changed
- Reworked `KrishnaTuneTheme` into a Metrolist-style root theming system with seed-color support, Android dynamic color fallback, optional pure-black dark mode handling, and global typography wiring.
- Updated app entry points to use the custom theme instead of raw `MaterialTheme`.
- Switched navigation, home, top bar/profile popup, section cards, and library surfaces to read from the shared Material theme palette rather than old screen-specific color constants.
- Promoted the Metro font family into the global typography scale so the rest of the app inherits the new theme system by default.

### Fixed
- Removed the incompatible `material-kolor` dependency path and kept the theme port within the project’s current Android Gradle Plugin and `compileSdk` baseline.
- Resolved Compose compatibility issues in the new player implementation so `./gradlew assembleDebug` succeeds on the current toolchain.

## [0.6.8] - 2026-04-19

### Fixed
- Corrected oversized typography on tablet-class devices by revising adaptive type multipliers to avoid upscaling on large widths.
- Added font-scale compensation in responsive typography to keep text stable when system font size is increased.
- Added targeted Samsung `SM-X115` device compensation profile to fix the reported on-device heading/title oversizing.
- Added explicit tablet caps for Home and section header stylized text so large screens keep Metro proportions.

## [0.6.7] - 2026-04-19

### Fixed
- Converted recent MetroList font-size overrides from fixed values to adaptive screen-width-based scaling.
- Added shared responsive typography scale utility and applied it to:
	- full player title/artist/header/timing/play label,
	- mini player title/artist,
	- top bar title and profile popup typography,
	- home section headings.
- Improved readability consistency across compact phones, normal devices, and tablets.

## [0.6.6] - 2026-04-19

### Fixed
- Rebalanced MetroList-font sizes across key app surfaces where text hierarchy still looked off.
- Increased readability and visual weight for:
	- player header/title/artist/time labels and play button text,
	- mini player title/artist,
	- top app bar title and profile popup headline/user name,
	- home section row headings.

## [0.6.5] - 2026-04-19

### Changed
- Applied MetroList `bbh_bartle` font usage to key KrishnaTune UI surfaces for closer visual parity:
	- full player labels and timing text,
	- mini player title/artist,
	- app top bar and profile popup headline,
	- home section row titles.
- Kept the MetroList-derived typography scale as the base theme and layered explicit decorative font usage on selected heading/hero text components.

## [0.6.4] - 2026-04-19

### Added
- Ported MetroList theme font assets (`bbh_bartle_regular.ttf` and `bbh_bartle.xml`) into app resources.
- Added MetroList-style theme token files for typography, spacing/dimensions, and custom font family aliases.

### Changed
- Updated `KrishnaTuneTheme` to use MetroList-style theming behavior: dynamic colors on Android 12+, Metro seed fallback palette, and optional pure-black dark mode.
- Replaced app typography scale with MetroList Material 3 typography tokens and updated shape radii to MetroList-style corner values.
- Switched player/miniplayer layout spacing to use centralized MetroList-derived theme spacing tokens.

## [0.6.3] - 2026-04-19

### Added
- Ported a MetroList-inspired mini player pill design with circular artwork progress and compact quick actions.
- Ported a MetroList-inspired full player layout with top header controls, large rounded artwork, scrubbable timeline slider, and transport controls.
- Added mini-player color tokens and accessibility strings for play, pause, close, and add-to-playlist actions.

### Changed
- Replaced previous KrishnaTune player and mini-player Compose UIs with MetroList-styled equivalents adapted to the existing `Song` data flow and app navigation.
- Kept swipe-down dismiss behavior for the full player while avoiding hard dependencies on MetroList-specific service/state providers.

## [0.6.2] - 2026-04-19

### Changed
- Removed unused legacy full-player file from `ui/screens/PlayerScreen.kt` after migrating active player implementation to `ui/player/PlayerScreen.kt`.
- Deleted obsolete empty directories left from old layout (`data/`, `domain/`, old `ui/components`, and empty screen component subfolders).

### Fixed
- Cleaned stale package paths to keep the MetroList-style structure consistent and avoid future import confusion.

## [0.6.1] - 2026-04-19

### Changed
- Reorganized core source files into MetroList-style package paths under `com.krishnatune`.
- Moved API layer to `api/` (`SaavnApi`), shared models to `models/` (`Song`, `HomeDataResponse`), and home state handling to `viewmodels/` (`HomeViewModel`, `HomeUiState`).
- Moved reusable UI pieces to `ui/component/` (`MiniPlayer`, `SongCardV2`) and full-player screen implementation to `ui/player/` (`PlayerScreen`).
- Updated imports and package references across navigation and screen files to match the new structure.

### Fixed
- Resolved Home screen compile errors after ViewModel relocation by importing `HomeViewModel` and `HomeUiState` from `com.krishnatune.viewmodels`.

## [0.5.0] - 2026-04-19

### Added
- Scaffolded a MetroList-inspired package folder structure under `android/app/src/main/java/com/krishnatune` by creating missing directories and `.gitkeep` placeholders.
- Added foundational folders for architecture expansion, including: `api`, `constants`, `db`, `di`, `eq`, `playback`, `viewmodels`, `widget`, `quicksettings`, `recognition`, `listentogether`, and additional UI feature folders.

### Changed
- Aligned project package layout more closely with the embedded `Metrolist` source structure to support future feature parity and modular growth.

## [0.4.1] - 2026-04-19

### Fixed
- Corrected Player screen vertical drag gesture callback from `onDrag` to `onVerticalDrag` to resolve Kotlin compile errors during `installDebug`.

## [0.6.0] - 2026-04-19

### Added
- Smooth swipe down animation to close the full screen player (`PlayerScreen`).

### Changed
- `AppNavigation` passes an `onClose` callback to `PlayerScreen` to handle the back navigation triggered by the swipe down gesture.


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
