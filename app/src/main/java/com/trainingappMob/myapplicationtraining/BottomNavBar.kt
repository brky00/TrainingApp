package com.trainingappMob.myapplicationtraining

import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home Button
        Button(onClick = { navController.navigate("home_page") }) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home"
            )
        }



        Button(onClick = { navController.navigate("workout_page") }) {
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = "Workout"
            )
        }

        // Meals Button
        Button(onClick = { navController.navigate("meal_page") }) {
            Icon(
                imageVector = Icons.Default.Restaurant,
                contentDescription = "Meals"
            )
        }

        // Profile Button
        Button(onClick = { navController.navigate("profil_page") }) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    BottomNavBar(
        navController = TODO()
    )
}
