# TCF Roulette - Android App

Native Android app for TCF French exam practice using Kotlin + Jetpack Compose.

## Requirements

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34 (Android 14)
- Min SDK 24 (Android 7.0)

## Building

### Open in Android Studio

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to this `android` folder
4. Wait for Gradle sync to complete

### Build Debug APK

```bash
./gradlew assembleDebug
```

The APK will be at: `app/build/outputs/apk/debug/app-debug.apk`

### Build Release APK/AAB

1. Create a keystore (if you don't have one):
```bash
keytool -genkey -v -keystore tcf-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias tcf
```

2. Create `app/signing.properties`:
```properties
storeFile=../tcf-release-key.jks
storePassword=your_password
keyAlias=tcf
keyPassword=your_password
```

3. Build release:
```bash
./gradlew assembleRelease  # APK
./gradlew bundleRelease    # AAB for Play Store
```

## Project Structure

```
app/src/main/
├── java/com/tcfroulette/app/
│   ├── MainActivity.kt          # Entry point
│   ├── ui/
│   │   ├── theme/               # Colors, Typography, Theme
│   │   └── screens/HomeScreen.kt
│   ├── data/
│   │   ├── database/            # Room: Entity, DAO, Database
│   │   └── repository/          # Data layer abstraction
│   └── viewmodel/               # UI state management
├── assets/
│   └── tcf.db                   # Pre-populated SQLite database
└── res/
    ├── drawable/                # Vector icons
    ├── mipmap-*/                # Launcher icons
    └── values/                  # Strings, colors, themes
```

## Features

- Random TCF practice prompt display
- Category, Task Type, Format, Scenario badges
- Purple gradient theme matching web app
- Offline-first (embedded SQLite database)
- Material 3 design

## Google Play Checklist

- [ ] Google Play Developer account ($25)
- [ ] App icon (512x512 PNG)
- [ ] Feature graphic (1024x500 PNG)
- [ ] Screenshots (min 2)
- [ ] Privacy policy URL
- [ ] App description
- [ ] Content rating questionnaire
- [ ] Signed AAB
