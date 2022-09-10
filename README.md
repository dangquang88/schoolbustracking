# RMIT School Bus Tracking
Nguyen Dang Quang - s3741190
Bui Tien Huy - s3777230
Pham Hoang Minh - s3871126
## 1.Application Description
![image](https://user-images.githubusercontent.com/57407516/189468563-50c8a342-e56e-4714-b703-7857b9a5f4ea.png)

The project generally develops a multifunctional application that provides a number of useful features to support RMIT students, particularly in school bus tracking. The product’s goal is to deliver the greatest school life experience not only for students but also the lecturers. The main features of the application include:

 - Account login (lecturers, students, admin)
 - See profile page + update profile information
 - Online calling + messaging between students/lectures
 - Uploading profile photo
 - School bus schedule and detail information
 - School bus real-time tracking (interacting with the map)
 - School bus travel history
 - List of students/lecturers
 - App Settings (adjust theme, view credit page)

## 2.Application UI (Figma)
Components

![image](https://user-images.githubusercontent.com/57407516/189468544-b3305ce6-25bb-4102-9688-e188de7c8e43.png)

![image](https://user-images.githubusercontent.com/57407516/189468603-ca2b972e-e6aa-4ef8-b2ca-bcd2d5c8375f.png)

## 3.Application Flow (Diagram)

![image](https://user-images.githubusercontent.com/57407516/189468581-d46e9714-619f-463a-ad2e-7aad1145ef0a.png)

## 4. Work Distribution:

Nguyen Dang Quang - s3741190:
- Online calling video + message
- Realtime tracking location
- UI design (Figma)
- Search bus
- Login/logout

Bui Tien Huy - s3777230:
- OTP Phone Verification
- Editing Profile Page
- Upload user avatar picture
- Preference setting page(dark mode, full screen)
- Merging code, fixing conflict bugs

Pham Hoang Minh - s3871126:
Bus management system for both students and teachers
- Student Register for bus with error handling
- Teacher Update on board status with error handling
System design
Code cleaning

## 5. Functionalities
_ Account login (teacher and student)
_ View profile page
_ Update profile page (avatar, password, phone number verification)
_ Online message and calling
_ Preference setting page(dark mode, full screen)
_ Location Tracking
_ Search Bus
_ Bus management: Students can register to buses and then the teachers can manage (view/update) the on-board status of the students

## 6. Technologies (Android Studio components)
We propose to utilize the following technologies in our project to effectively develop the application (this is just estimation of what we need for the project, not the complete technology stack):

- Java - Android studio
Listview and adapter for displaying data fetched from the server (Firebase)
Permission for FINE_LOCATION and COARSE_location, INTERNET for location-based functionalities and related API such as LocationService (fusedLocation) or getCurrentLocation 
Permission for CAMERA, storage for online calling, messaging functionalities and related API such as Camera, SurfaceView, MediaRecorder, etc
Other basic components such as Button, Textview, etc for the app’s user interface

- Google API
Maps SDK for android, used for school bus tracking
Directions API for android, used for creating routes between locations

- Firebase
Cloud messaging for messaging feature
Firestore for storing data
Firebase authentication on android for authentication
OTP message


