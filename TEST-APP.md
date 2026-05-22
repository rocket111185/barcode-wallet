# Testing The App

This project uses the Android Gradle wrapper, so all commands should be run from the repository root.

## 1. Test With An Emulator

Make sure the Android CLI tools are available in your shell:

```fish
set -gx JAVA_HOME $HOME/android-studio/jbr
set -gx ANDROID_HOME $HOME/Android/Sdk
set -gx PATH $ANDROID_HOME/platform-tools $ANDROID_HOME/emulator $PATH
```

Build the debug APK:

```bash
./gradlew assembleDebug
```

List available virtual devices:

```bash
emulator -list-avds
```

Start one emulator:

```bash
emulator @YOUR_AVD_NAME
```

Install and launch the app:

```bash
./gradlew installDebug
adb shell monkey -p com.rocket111185.barcodewallet 1
```

## 2. Test With A Real Phone Over USB

On the phone:

1. Enable Developer Options.
2. Enable USB debugging.
3. Connect the phone by USB.
4. Accept the RSA debugging prompt.

Check that ADB sees the phone:

```bash
adb devices
```

The device should appear with the `device` status.

Build, install, and launch the app:

```bash
./gradlew assembleDebug
./gradlew installDebug
adb shell monkey -p com.rocket111185.barcodewallet 1
```
