1. Application Description

The project generally develops a multifunctional application that provides a number of useful features to support RMIT students, particularly in school bus tracking. The product’s goal is to deliver the greatest school life experience not only for students but also the lecturers. The main features of the application include:

 Account login (lecturers, students, admin)
 See profile page + update profile information
 Online calling + messaging between students/lectures
 Uploading profile photo
 School bus schedule and detail information
 School bus real-time tracking (interacting with the map)
 School bus travel history
 List of students/lecturers
 App Settings (adjust theme, view credit page)



Application UI (Figma)
Components


Application Flow (Diagram)


Technologies (Android Studio components)

We propose to utilize the following technologies in our project to effectively develop the application (this is just estimation of what we need for the project, not the complete technology stack):

Java - Android studio
Listview and adapter for displaying data fetched from the server (Firebase)
Permission for FINE_LOCATION and COARSE_location, INTERNET for location-based functionalities and related API such as LocationService (fusedLocation) or getCurrentLocation 
Permission for CAMERA, storage for online calling, messaging functionalities and related API such as Camera, SurfaceView, MediaRecorder, etc
Other basic components such as Button, Textview, etc for the app’s user interface

Google API
Maps SDK for android, used for school bus tracking
Directions API for android, used for creating routes between locations

Firebase
Cloud messaging for messaging feature
Firestore for storing data
Firebase authentication on android for authentication
OTP message

