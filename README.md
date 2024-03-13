# Star Knight 🤺

The Star knight game is a 2D platformer game developed using the LibGDX framework.

## Firebase 🔥

This project uses Firebase for the backend. The Firebase SDK is included in the Android module.

The project is set up to use Firebase Realtime Database.

### Using Firebase 🧑🏽‍🚒

Firebase is Android specific, i.e you cannot access Firebase methods from the Desktop or Core files.
All methods must be called through a class that implements the "FirebaseInterface" located in "core".
Any actual Firebase logic should be added into "AndroidFirebase".

LibGDX on compile time will "replace" the methods in "CoreFirebase" with the ones in the Android module,
since it knows it is an Android project.

For some explenation on how this works, see this [video](https://www.youtube.com/watch?v=WhuWqWVJ-_Y).
