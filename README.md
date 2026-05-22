# Barcode Wallet

Barcode Wallet is a native Android app for storing, viewing, and scanning QR codes and barcodes.

The project is being rebuilt with Android-first tools: Kotlin, Jetpack Compose, Room, and ML Kit. The initial Android project targets API level 33 for Android 13 support.

## CLI Workflow

For Fish shell, configure the Android CLI environment once:

```fish
set -gx JAVA_HOME $HOME/android-studio/jbr
set -gx ANDROID_HOME $HOME/Android/Sdk
set -gx PATH $ANDROID_HOME/platform-tools $ANDROID_HOME/emulator $PATH
```

Use the Gradle wrapper from the repository:

```bash
./gradlew assembleDebug
./gradlew test
./gradlew installDebug
```

To build with `compileSdk = 33`, install Android SDK Platform 33 if it is not already installed.
