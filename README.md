# YemekCalendar

### 🍽️ **A Monthly Food Menu Application**
This application, built using Jetpack Compose, provides an intuitive and visually appealing way to browse and manage monthly food menus for selected institutions. Detailed overview of its features and capabilities are below.

[![License](https://img.shields.io/badge/License-MIT-lightgray.svg?style=flat-square)](https://spdx.org/licenses/MIT.html)
[![Latest release](http://img.shields.io/badge/beta-0.1.0-blue.svg?style=flat-square)](./)
[![minSdk](https://img.shields.io/badge/minSdk-28-green.svg?style=flat-square)](https://spdx.org/licenses/MIT.html)
[![targetSdk](http://img.shields.io/badge/targetSdk-34-blue.svg?style=flat-square)](./)

<p align="center">
<a href="https://www.android.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/android/android-official.svg" alt="flutter" width="100" height="60"/> </a>
<a href="https://dart.dev" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/dartlang/dartlang-ar21.svg" alt="dart" width="100" height="60"/> </a>   
<a href="https://kotlinlang.org/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/kotlinlang/kotlinlang-ar21.svg" alt="kotlin" width="100" height="60"/> </a>
<a href="https://firebase.google.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/firebase/firebase-ar21.svg" alt="flutter" width="100" height="60"/> </a> 
<a href="https://dagger.dev/hilt/" target="_blank" rel="noreferrer"> <img src="screenshots/dagger_hilt" alt="Android Dagger Hilt Library" width="100" height="60"/> </a> 
<a href="https://developer.android.com/jetpack/androidx/releases/room" target="_blank" rel="noreferrer"> <img src="screenshots/room.svg" alt="Android Room Database" width="100" height="60"/> </a> 
<a href="https://developer.android.com/jetpack/guide#recommended-app-arch" target="_blank" rel="noreferrer"> <img src="screenshots/mvvm.svg" alt="MVVM architecture" width="100" height="60"/> </a> 
</p>

</br>

|                              |                              |                              |                       |
| :---------------------------:|:----------------------------:|:----------------------------:|:---------------------:|
| ![](screenshots/image1.png)  |  ![](screenshots/image2.png) | ![](screenshots/image3.png)  |  ![](screenshots/image4.png)|



* [Introduction](#introduction)
* [Key Features](#key-features)
* [Architecture](#architecture)
* [Installation](#installation)
* [Usage](#usage)
* [Known issues and limitations](#known-issues-and-limitations)
* [License](#license)
* [Authors and history](#authors-and-history)
* [Acknowledgments](#acknowledgments)


Introduction
------------

Key Features
------------

🖌️ **Theming and Design**
- **Predefined Custom Themes**: Choose from a selection of beautifully crafted themes.
- **Material You**: Dynamic theming support to match the system’s color scheme for a personalized look and feel.

🌍 **Multilingual Support**
- **Languages**: Supports Turkish, English, and Arabic, ensuring accessibility for a broader audience.

📶 **Local First**
- **Offline Functionality**: The app works seamlessly offline after the initial data fetch. Users can access menus without an internet connection.
- **Remember Me**: Users can stay logged in if they mark the 'Remember Me' checkbox, bypassing the login screen on subsequent launches.

🔄 **Firebase Integration**
- **Realtime Database**: Keeps data up-to-date with real-time synchronization.
- **Firebase Storage**: Allows users to save their profile pictures securely.

📅 **Calendar Integration**
- **Add to Calendar**: Users can add daily menus to their local calendars for easy access and reminders.
- **Browse by Food Item**: See which days specific food items are served with a dedicated calendar view.

👥 **User Account Management**
- **Account Creation and Login**: Supports account creation, login, and password reset via email.
- **Profile Picture**: Users can upload and save profile pictures using Firebase Storage.

🔄 **Data Synchronization**
- **Worker API**: Schedules hourly data refreshes to ensure the latest information is available.
- **Service API**: Runs a background service to sync data from the cloud in real-time, keeping the app updated whether in the foreground or background.

🛠️ **User Experience Enhancements**
- **Back to Top Button**: A convenient button appears when scrolling down for quick navigation back to the top.
- **Pull-to-Refresh**: Easily refresh the data within the app to see the most recent updates.


Architecture
------------

![App Packaging Structure](#)

##### App Structure

The app follows the MVVM (Model-View-ViewModel) architecture. MVVM helps in separating the user interface logic from business logic, making the code more modular and easier to manage.

For more information on MVVM architecture, refer to [this link](https://developer.android.com/jetpack/guide#recommended-app-arch).

![MVVM Architecture](#)

##### Database Schema

The app uses two databases: one online and one offline.

- **Online Database**: Uses Firebase Realtime Database for real-time updates.
  ![Online Database Schema](#)


Installation
-------------

Usage
------

Known issues and limitations
----------------------------


License
-------


Authors and history
---------------------------

* Esam Bashir : The original author.


Acknowledgments
---------------

- **Offline Database**: Stores data locally for offline access and faster performance.
  ![Offline Database Schema](#)
