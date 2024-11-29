# Fitmeals Training & Meal App

## Overview
Fitmeals is a Kotlin-based Android application developed using Jetpack Compose, Firebase Firestore, and Firebase Authentication. The app is designed to help users maintain their fitness goals by tracking workouts and meals, earning points, and managing their profiles.

This project is part of an academic assignment for our IT and Information Systems MOB3000 course.

## Features
1. User Authentication
   Users can register with their email, username, name, and birthdate.
   Login functionality with Firebase Authentication.
   Account deletion option with a confirmation prompt.
2. Workout Tracking
   Users can register their workouts with details such as:
   Exercise type.
   Reps and sets.
   Duration in minutes.
   Calculates average total calories burned based on exercise intensity.
   Tracks the last 7 recorded workouts in a list.
   Users can update or delete existing workouts.
3. Meal Tracking
   Users can log meals with details such as:
   Name.
   Calories.
   Protein content.
   Tracks the last 7 recorded meals in a list.
   Users can update or delete existing meals.
4. Points System
   Users earn points for creating and deleting workouts or meals:
   Points vary based on calories burned during workouts.
   Deleting entries deducts points.
5. Profile Management
   Displays user information: name, username, email, birthdate, and total score.
   Users can edit their profile information or log out.
6. Dynamic Goals
   Choose between:
   Building muscle: Meals with high protein.
   Losing weight: Meals with low calories.
   Suggests meals dynamically from Firestore.
7. Navigation
   Intuitive navigation with a BottomNavBar to switch between:
   Home
   Workouts
   Meals
   Profile

## Technologies Used
Frontend:
Jetpack Compose: For building the UI.
Coil: For optimized image loading.
Backend:
Firebase Firestore: Database for storing meals, workouts, and user information.
Firebase Authentication: User login and registration.

## Installation

## Prerequisites
Install Android Studio.
Configure Firebase Project with:
Firestore Database.
Authentication (Email/Password).

### Steps to Run
Clone the repository:
git clone https://github.com/brky00/TrainingApp.git
Open the project in Android Studio.
Sync the project with Gradle.
Build and run the app on an emulator or physical device.

## How It Works

### User Flow:
Start at the Welcome Screen.
Register or log in to access the app.
Navigate between Home, Workouts, Meals, and Profile using the BottomNavBar.

### Backend Integration:

Firebase Authentication validates users.
Firestore manages data for meals, workouts, and profiles.
Points System:

+2 points for meals or workouts logged.
+3 points for high-calorie workouts (>700 calories).
-2 or -3 points for deletions.