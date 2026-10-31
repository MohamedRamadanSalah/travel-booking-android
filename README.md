# Travel Booking (Android)

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white) ![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white) ![minSdk](https://img.shields.io/badge/minSdk-24-blue) [![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A native Android travel app in Java: browse destinations by category, discover popular and recommended places, view place details and book a ticket. Destination data loads live from **Firebase Realtime Database**.

## Features

- Splash, login and sign-up screens
- Home with an image slider, categories, and popular and recommended places (RecyclerView adapters)
- Place details and ticket booking screens
- User profile
- Live data from Firebase Realtime Database

## Tech stack

Java · Android SDK (minSdk 24, targetSdk 35) · Material Components · ConstraintLayout · RecyclerView · Firebase Realtime Database · Gradle (Kotlin DSL)

## Firebase setup

The Firebase config file is not committed. To run the app:

1. Create a Firebase project and add an Android app with the package name shown in `app/build.gradle.kts`.
2. Enable **Realtime Database** and import your data.
3. Download `google-services.json` into `app/`.

## License

[MIT](LICENSE) © 2025 Mohamed Ramadan
