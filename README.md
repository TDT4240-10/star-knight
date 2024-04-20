# Star Knight 🤺

The Star knight game is a 2D platformer game developed using the LibGDX framework.

## Getting Started 🚀

### Prerequisites 📋

- Java 17 or higher
- Android Studio

### Installation 🔧

1. Clone the repository
2. Open the project in Android Studio
3. Set up an android emulator or connect a physical device
4. Configure the project to run as an Android project
5. Enjoy the game 🎮

### Deploy to Android 📱

1. Navigate to the root of the project
2. Run the following command
   ```sh
   ./gradlew android:assembleRelease
   ```

## Structure 🏗

```
├── android/                        Contains the Android specific code.
│ ├── src/no/ntnu/game
│ │ ├── AndroidFirebase.java        All Firebase logic.
│ │ ├── AndroidLauncher.java        Main class for the Android module.
│ │ ├── FirebasePlayer.java         Firebase player class.
│ ├── google-services.json          Firebase configuration file.
├── core/                           Contains the main game logic.
│ ├── src/no/ntnu/game
│ │ ├── Callback/                   Contains callback implementations.
│ │ ├── Controllers/                Contains the different controllers of the game.
│ │ ├── Factory/                    Contains the different factories of the game.
│ │ ├── Firestore/                  Contains the Firestore helper classes.
│ │ ├── Models/                     Contains the different models of the game.
│ │ ├── Settings/                   Contains setting implementations.
│ │ ├── Sound/                      Contains sound implementations.
│ │ ├── Views/                      Contains the different views of the game.
│ │ ├── StarKnight.java             Main class for the game.
│ │ ├── CoreFirebase.java           Core class that implements the FirebaseInterface.
│ │ ├── FirebaseInterface.java      Interface for Firebase methods.
│ │ ├── FirebaseCompatible.java     Firebase helper Interface
├── gradle/                         Contains the gradle wrapper.
├── .gitignore                      Contains the files that should be ignored by git.
├── .README.md                      Documentation
└── ...

```

## Firebase 🔥

This project uses Firebase for the backend. The Firebase SDK is included in the Android module.

The project is set up to use:

- Firebase Realtime Database. [Get Started](https://firebase.google.com/docs/database/quickstart#java_1)🚀
- Firebase Firestore. [Get Started](https://firebase.google.com/docs/firestore/quickstart#java_1)🚀

### Using Firebase in the project 🧑🏽‍🚒

Firebase is Android specific, i.e you cannot access Firebase methods from the Desktop or Core files.
All methods must be called through a class that implements the "FirebaseInterface" located in "core".
Any actual Firebase logic should be added into "AndroidFirebase".

For some explanation on how this works, see this [video](https://www.youtube.com/watch?v=WhuWqWVJ-_Y).
