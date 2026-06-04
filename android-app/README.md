# MONO Android

This is a Kotlin/Jetpack Compose Android rewrite of the original iOS MONO clone in this repository.

## Requirements

- Android Studio with Android SDK Platform 36
- JDK 17, normally bundled with Android Studio
- A phone with USB debugging enabled, or an Android emulator

## Run

```powershell
cd C:\zyh\MONO\android-app
.\gradlew.bat testDebugUnitTest
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
```

If Gradle cannot find the SDK, copy `local.properties.example` to `local.properties` and set `sdk.dir` to your Android SDK path.

## Asset Migration

The script below copies PNG files from the iOS asset catalog into Android drawable density folders:

```powershell
cd C:\zyh\MONO\android-app
.\scripts\migrate_xcassets.ps1
```

The Android app currently uses mock data and remote placeholder images so it can run without the old MONO API.
