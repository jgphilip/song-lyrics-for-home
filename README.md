# BZ Christian Lyrics

An Android app for browsing Christian songs by genre and reading their lyrics. The catalog is bundled with the app, so songs are available offline.

## Features

- Browse the song catalog on the Home screen.
- Browse songs by genre.
- Open a song to view its artist, genre, language, and lyrics.
- Load the bundled song catalog into a local Room database on first use.

The project is an early-stage prototype. The Settings screen is currently a placeholder.

## Tech stack

- Kotlin and Jetpack Compose
- Material 3
- Navigation Compose
- Room for local song storage
- Hilt for dependency injection
- DataStore for preferences
- Gson for reading the bundled JSON catalog

## Requirements

- Android Studio with Android SDK 35 installed
- JDK 21 (the Gradle daemon toolchain is configured for Java 21)

The app supports Android 7.0 (API 24) and later.

## Build and run

Clone the repository and open it in Android Studio, or build it from the project root:

```bash
./gradlew :app:assembleDebug
```

Install the debug build on a connected device or emulator:

```bash
./gradlew :app:installDebug
```
