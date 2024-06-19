# YemekCalendar

### 🍽️ **A Monthly Food Menu Application**
This application, built using Jetpack Compose, provides an intuitive and visually appealing way to browse and manage monthly food menus for selected institutions. Detailed overview of its features and capabilities are below.

![Android](https://img.shields.io/badge/android-green)
![Kotlin](https://img.shields.io/badge/kotlin-grey)
![minSdk](https://img.shields.io/badge/minSdk-28-green)
![targetSdk](https://img.shields.io/badge/targetSdk-34-blue)


<p>
  <img src="resources/Screenshot_1.png" width="250" />
  <img src="resources/Screenshot_2.png" width="250" />
  <img src="resources/Screenshot_3.png" width="250" />
</p>



## Key Features

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

#### Architecture

![App Packaging Structure](#)

##### App Structure

The app follows the MVVM (Model-View-ViewModel) architecture. MVVM helps in separating the user interface logic from business logic, making the code more modular and easier to manage.

For more information on MVVM architecture, refer to [this link](https://developer.android.com/jetpack/guide#recommended-app-arch).

![MVVM Architecture](#)

##### Database Schema

The app uses two databases: one online and one offline.

- **Online Database**: Uses Firebase Realtime Database for real-time updates.
  ![Online Database Schema](#)

- **Offline Database**: Stores data locally for offline access and faster performance.
  ![Offline Database Schema](#)
