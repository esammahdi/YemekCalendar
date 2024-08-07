<p align="center">
<a href="https://github.com/esammahdi/YemekCalendar/" target="_blank" rel="noreferrer"> <img src="readme_resources/ycalendar-logo.png" alt="yemek calendar logo" width="400" height="300"/> </a>  
</p>

# YemekCalendar

### 🍽️ **A Monthly Food Menu Application**
This application, built using Jetpack Compose, provides an intuitive and visually appealing way to browse and manage monthly food menus for selected institutions. Detailed overview of its features and capabilities are below.

[![License](https://img.shields.io/badge/License-GPL3.0-lightgray.svg?style=flat-square)](https://spdx.org/licenses/MIT.html)
[![Latest release](http://img.shields.io/badge/beta-1.0-blue.svg?style=flat-square)](./)
[![minSdk](https://img.shields.io/badge/minSdk-28-green.svg?style=flat-square)](https://spdx.org/licenses/MIT.html)
[![targetSdk](http://img.shields.io/badge/targetSdk-34-blue.svg?style=flat-square)](./)

<p align="center">
<a href="https://www.android.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/android/android-official.svg" alt="flutter" width="100" height="60"/> </a>
<a href="https://developer.android.com/develop/ui/compose" target="_blank" rel="noreferrer"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/jetpackcompose/jetpackcompose-original.svg" alt="jetpack compose" width="100" height="60" />      
<a href="https://firebase.google.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/firebase/firebase-ar21.svg" alt="flutter" width="120" height="70"/> </a> 
<a href="https://kotlinlang.org/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/kotlinlang/kotlinlang-ar21.svg" alt="kotlin" width="100" height="60"/> </a>
</p>

</br>

<p align="center">
<a href="https://dagger.dev/hilt/" target="_blank" rel="noreferrer"> <img src="readme_resources/dagger_hilt.svg" alt="Android Dagger Hilt Library" width="120" height="80"/> </a> 
<a href="https://developer.android.com/jetpack/androidx/releases/room" target="_blank" rel="noreferrer"> <img src="readme_resources/room.svg" alt="Android Room Database" width="100" height="60"/> </a> 
<a href="https://developer.android.com/jetpack/guide#recommended-app-arch" target="_blank" rel="noreferrer"> <img src="readme_resources/mvvm.svg" alt="MVVM architecture" width="150" height="70"/> </a> 
</p>

</br>

## Table of Contents

- [Screenshots](#screenshots)
  - [Authentication](#authentication)
  - [Calendar](#calendar)
  - [Settings](#settings)
  - [Dynamic Theme](#dynamic-theme)
- [Introduction](#introduction)
- [Key Features](#key-features)
  - [Theming and Design](#theming-and-design)
  - [Multilingual Support](#multilingual-support)
  - [Local First](#local-first)
  - [Firebase Integration](#firebase-integration)
  - [Calendar Integration](#calendar-integration)
  - [User Account Management](#user-account-management)
  - [User Experience Enhancements](#user-experience-enhancements)
- [Upcoming Features in v2.0](#upcoming-features)
- [Architecture](#architecture)
  - [App Structure](#app-structure)
  - [Database Schema](#database-schema)
- [Installation](#installation)
- [Usage](#usage)
- [Known Issues and Limitations](#known-issues-and-limitations)
  - [App-Wide Issues](#app-wide-issues)
  - [Screen-Specific Issues](#screen-specific-issues)
- [License](#license)
- [Authors and History](#authors-and-history)
- [Acknowledgments](#acknowledgments)

  
Screenshots
------------
* ### Authentication
<p align="center">
  <img src="readme_resources/screenshots/Preview/Authentication/image1.png" width="150" />
  <img src="readme_resources/screenshots/Preview/Authentication/image2.png" width="150" />
  <img src="readme_resources/screenshots/Preview/Authentication/image3.png" width="150" />
</p>

<!--
https://github.com/esammahdi/YemekCalendar/assets/95491137/30c1168e-f722-440e-bd67-fdfd0ff91d23

https://github.com/esammahdi/YemekCalendar/assets/95491137/9fe62cba-b307-44f8-8814-d976da71b4fa

https://github.com/esammahdi/YemekCalendar/assets/95491137/1ae4b2fa-ff78-4403-91d0-1fcaa3b50ac4
-->

* ### Calendar
<p align="center">
  <img src="readme_resources/screenshots/Preview/FoodItems/image1.png" width="150" />
  <img src="readme_resources/screenshots/Preview/FoodItems/image3.png" width="150" />
  <img src="readme_resources/screenshots/Preview/FoodItems/image4.png" width="150" />
  <img src="readme_resources/screenshots/Preview/FoodItems/image5.png" width="150" />
</p>



<!--
https://github.com/esammahdi/YemekCalendar/assets/95491137/63ea23b9-494e-45b1-ba8a-5001ddca0204 

https://github.com/esammahdi/YemekCalendar/assets/95491137/c91c8888-9257-46ab-be79-ff5288a7f2cc

https://github.com/esammahdi/YemekCalendar/assets/95491137/4295e422-bd2a-4a44-bcb3-7afb1a532ab3
-->
 
* ### Settings
<p align="center">
  <img src="readme_resources/screenshots/Preview/Settings/image1.png" width="150" />
  <img src="readme_resources/screenshots/Preview/Settings/image2.png" width="150" />
  <img src="readme_resources/screenshots/Preview/Settings/image3.png" width="150" />
  <img src="readme_resources/screenshots/Preview/Settings/image4.png" width="150" />
  <img src="readme_resources/screenshots/Preview/Settings/image5.png" width="150" />
</p>

* ### Dynamic Theme
<p align="center">
  <img src="readme_resources/screenshots/Preview/Dynamic Theme/image1.png" width="150" />
  <img src="readme_resources/screenshots/Preview/Dynamic Theme/image2.png" width="150" />
  <img src="readme_resources/screenshots/Preview/Dynamic Theme/image3.png" width="150" />
</p>

<!--
https://github.com/esammahdi/YemekCalendar/assets/95491137/3b0368ba-35e7-46c1-b2fa-39d72b495526
-->

Introduction
------------

Key Features
------------

<a name="theming-and-design"></a>
🖌️ **Theming and Design**
- **Predefined Custom Themes**: Choose from a selection of beautifully crafted themes.
- **Material You**: Dynamic theming support to match the system's color scheme for a personalized look and feel.

<a name="multilingual-support"></a>
🌍 **Multilingual Support**
- **Languages**: Supports Turkish, English, and Arabic, ensuring accessibility for a broader audience.

<a name="local-first"></a>
📶 **Local First**
- **Offline Functionality**: The app works seamlessly offline after the initial data fetch. Users can access menus without an internet connection.
- **Remember Me**: Users can stay logged in if they mark the 'Remember Me' checkbox, bypassing the login screen on subsequent launches.

<a name="firebase-integration"></a>
🔄 **Firebase Integration**
- **Realtime Database**: Keeps data up-to-date with real-time synchronization.
- **Firebase Storage**: Allows users to save their profile pictures securely.

<a name="calendar-integration"></a>
📅 **Calendar Integration**
- **Add to Calendar**: Users can add daily menus to their local calendars for easy access and reminders.
- **Browse by Food Item**: See which days specific food items are served with a dedicated calendar view.

<a name="user-account-management"></a>
👥 **User Account Management**
- **Account Creation and Login**: Supports account creation, login, and password reset via email.
- **Profile Picture**: Users can upload and save profile pictures using Firebase Storage.

<a name="user-experience-enhancements"></a>
🛠️ **User Experience Enhancements**
- **Back to Top Button**: A convenient button appears when scrolling down for quick navigation back to the top.
- **Pull-to-Refresh**: Easily refresh the data within the app to see the most recent updates.

### The following features are either yet to be implemented or have some problems. See  [Known issues and limitations](#known-issues-and-limitations) for more info.

📅 ~~**Calendar Source**~~

API Link Integration: Users can add a custom calendar source by providing an API link in the settings page. This allows for personalized and up-to-date menu data directly from the user’s preferred source.

🔄  ~~**Data Synchronization**~~ 
- **Worker API**: Schedules hourly data refreshes to ensure the latest information is available.
- **Service API**: Runs a background service to sync data from the cloud in real-time, keeping the app updated whether in the foreground or background.

🔔 ~~**Notifications**~~
- **Data change notifications** : Any remote data change event will result in a notifcation if the app is in the background (Only supported for firebase)

### Upcoming Features

#### Version 2.0:
*API-based structure:*
- Settings menu includes a field for the user to provide an API link. 
- Food menu data fetches through the given API, making it more adaptable and dynamic.
- Online and Local DB Schemas will be changed to use an enum for MealType and FoodType.

*Language Support for Food Items:*
  - As of now the language option only affects the app itself. The information from the online source you choose is not known and thus can not be translated. However, in the upcoming API based version, an optional `Food Items in Locale $locale ` is planned to be introduced. This will be an optional choice for the api provider. For example, if the initial language was English and you changed it to Arabic, the app will make a request to the API to get the food items in that language if available, if not, or if there is a connection problem it will use the default language the api provides instead. 
    
*Worker API:*
  - Schedules hourly data refreshes to ensure the latest information is available.
    
*Service API:*
  - Runs a background service to sync data from the cloud in real-time.
    
*Notifications:*
  - Notifications for data change events when the app is in the background

*Improved Themes:*
  - Some of the current themes have problems. They will be fixed along with introducing new themes.
    

Architecture
------------

#### App Structure

The app is single-moduled and is structured by feature as follows :

![App Packaging Structure](readme_resources/app_structure.png)

The app follows the MVVM (Model-View-ViewModel) architecture. MVVM helps in separating the user interface logic from business logic, making the code more modular and easier to manage.

For more information on MVVM architecture, refer to [this link](https://developer.android.com/jetpack/guide#recommended-app-arch).

#### Database Schema

The app uses two databases: one online and one offline. Their schemas can be seen below. Note that in the next version the schemas will be changed as a `FoodType` and a `MealType` enums will be introduced instead of using plain strings, since the types are already preset. 

- **Online Database**: Uses Firebase Realtime Database for real-time updates.
  
  ![Online Database Schema](readme_resources/online_db_diagram.svg)
  
- **Local Database**: Stores data locally for offline access and faster performance
  
  ![Offline Database Schema](readme_resources/local_db_diagram.svg)
  


Installation
-------------
You can download the showcase version which comes with prefilled data for demonstration purposes from the _releases page_ [here](). But if you want to add a source of your own,
as of now, you have to clone the repo and build the app yourself after connecting it to the firebase service that you manage/your data provider gave you.

However, in the next version of the app it is planned for the app to be API based. At that point ready apk files will be provided here on github and possibly on the Play Store.

Usage
------
## **_To Be Updated_**

### Five types of calendar cards:

<table>
  <tr>
    <td style="text-align: center; padding: 10px;">
      <h5>Normal Day Card</h5>
    </td>
    <td style="text-align: center; padding: 10px;">
      <h5>Updated Day Card</h5>
    </td>
    <td style="text-align: center; padding: 10px;">
      <h5>Weekend Day Card</h5>
    </td>
    <td style="text-align: center; padding: 10px;">
      <h5>Special Holiday Day Card</h5>
    </td>
    <td style="text-align: center; padding: 10px;">
      <h5>Canceled Day Card</h5>
    </td>
  </tr>
  <tr>
    <td style="text-align: center; padding: 10px;">
      <img src="readme_resources/screenshots/Normal_Card.png" alt="Normal Day Card" width="150"/>
    </td>
    <td style="text-align: center; padding: 10px;">
      <img src="readme_resources/screenshots/Updated_Card.png" alt="Updated Day Card" width="150"/>
    </td>
    <td style="text-align: center; padding: 10px;">
      <img src="readme_resources/screenshots/Weekend_Card.png" alt="Weekend Day Card" width="150"/>
    </td>
    <td style="text-align: center; padding: 10px;">
      <img src="readme_resources/screenshots/Holiday_Card.png" alt="Special Holiday Day Card" width="150"/>
    </td>
    <td style="text-align: center; padding: 10px;">
      <img src="readme_resources/screenshots/Canceled_Card.png" alt="Canceled Day Card" width="150"/>
    </td>
  </tr>
</table>



Known issues and limitations
----------------------------

### App Wide Issues

- **Language Support on previous APIs** :

  The app language does not change when the user selects a new language on devices with API level 28. Bigger APIs has yet to be tested. The reason for this is undetermined yet but it was observed that the locale is succuffully changed despite the language not changing.

- **Support for only Gregorian Calendar** :

     As of now, only gregorian calendar is supported. The dates are handled through unix-epoch time stamps, but the months list, date formatting, add event to calendar ..etc are coded with consideration for gregorian calendar only.

- **System Bar Hidden** :
     While enabling edgeToedge(), the top system bar dissapears (gets drawn over by the app) sometimes depending on the theme on some devices. And on others it just dissapears until a some system-theme is enabled on that device and then all the app themes will work jusst fine.

- **Background Worker** :

  The refresh data worker responsible for refreshing the data every two hours does not work despite it being registered as can be seen from the 'App Inspection' panel in Android Studio.

- **Notifications** :

    Notifications are not yet set up. The foreground service that is responsible for handling the events and showing either a Toast (when inside the app) or a notification is set up, but the notifications themselves are not yet created.
 
- **Calendar Source Settings** :
   * There are no settings for a calendar source that the user can provide himself yet.
   * A simple option to add the Firebase Configurations (Auth Domain, Realtime DB url and StorageBucket) could be implemented but that is too tightly coupled with Firebase and requires each provider to give the user three links. It also requires the provider make a logic to give access to that specific app in some way since the provider can not use the 'App Check' feature.
   * So instead of that it was decided to make the app API based and thus, completely Firebase-independent. The data layered is already structured in such way. What is required is to make an OpenAPI document with the required specifications. But this requires changing the  database schema. So this was left for the next version of the app. 
   * As of now, the sources are hard coded with the app and requires the one who build it to provide the Firebase services that follow the required rules.

- **YemekCalendarDropdownList** :
  
   1- The dropdown list background color is not suitable.
  
   2- The width of the dropdown is not consistent. Some times it takes the width of the entire screen and some times it wraps it's content width.
      The appropriate behavior is for it to match the width of the enclosing outlined button.

### Screen Specific Issues

- **Registration and Reset Password Screens** : 

  Upon succufull registraion or resetting a password, a confirmation dialog shows up. It then automatically redirects the user to the logging page.

  The problem is that the dialog only dissapears after the page in the back already changed to the login page. This behavior leaves a bad UX. The desired outcome is for the navigation process to happen only after the dialog dissapears.
  
- **CalendarScreen** :
  * No Language support other than the online-source provided one : While the rest of the app support other languages the food items names are gotten from the online db directly and thus have no translation to other languages. To fix this, the entire schema of the database needs to be altered, in a way that makes every source have multiple food items lists, one list for each locale (e.g: food_items_en, food_items_tr ..etc). But this is too bothersome and thus have been left for the next version of the app which is planned to be API based.  
  * When refreshing the data through pull-refresh, the loading screen only waits for the instiutions list to load since the calendar day items require some time to load so at first the shimmer loading screen lifted but the page shows the 'No Calendar For this Month' message sine there are no items in the list yet. It takes some seconds until the items are loaded. The appropriate behavior is for the loading screen to continue until the calendar day items are loaded.
  * The month name in the month navigation bar is not localized when the language is changed. This is because the month list is an enumeration of gregorian calendar months. To solve this a logic to get the localized month name through `SimpleDateFormat("MMMM", Locale.getDefault()).format(date)` should be implemented.  
  

- **FoodDetailsScreen** :

  The header (that shows the food name and image) shows a vertical line with the primary color on the edges when in collapsed state.

- **Appearance Screen** :
    * The color schemes of some predefined-themes is not good and should be changed.
    * The 'Disabled Theme' should not be included in the theme list.   

License
-------


Authors and history
---------------------------

* Esam Bashir : The original author.


Acknowledgments
---------------
* [Placeholder]() : For the initial code of the authentication feature. 
* [ Abdellah Ibn El Azraq (BBlueCoder) ](https://github.com/BBlueCoder/ViDown/tree/master) : His article about using Hilt with was realy helpfull. I also modeled some parts of the readme file after his project's readme file. 
* [Logo.com](https://logo.com/) : Used to design the application logo as well as some other logos for the readme file.
* [Previewed](https://previewed.app/) : Used to design the screenshots previews above.
* Icons :
   - [Placeholder]() for the appearance icon in the Settings Screen.
   - [Placeholder]() for the WeekEnd Day Card illustration.
   - [Placeholder]() for the SpecialHoliday Day Card illustration.
   - [Placeholder]() for the Canceled Day Card illustration.
