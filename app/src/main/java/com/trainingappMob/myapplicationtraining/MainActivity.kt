package com.trainingappMob.myapplicationtraining


import BottomNavBar
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firestore = Firebase.firestore
        enableEdgeToEdge()
        setContent {
            MyApplicationTrainingTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {

                        BottomNavBar(navController = navController)
                    } ) {
                        paddingValues ->
                    // padding in ui elements with paddingValues
                    NavigationBetweenPages(navController, paddingValues)

                }
            }

        }
    }
}


@Composable
fun NavigationBetweenPages(navController: NavHostController, paddingValues: PaddingValues) {

    NavHost(
        navController = navController,
        startDestination = "home_page",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = "home_page") {
            HomePage(navController)
        }
        composable(route = "meal_page") {
            RegisterMeal(navController)
        }
        composable(route = "workout_page") {
            RegisterWorkout(navController)
        }
        composable(route = "profil_page") {
            ProfilPage(navController)
        }
        composable(route="navbar") {
            BottomNavBar(navController)
        }
    }
}






@Preview(showBackground = true)
@Composable
fun TrainingPreview() {
    MyApplicationTrainingTheme {
        val navController = rememberNavController()


        val paddingValues = PaddingValues(0.dp)

        NavigationBetweenPages(
            navController = navController,
            paddingValues = paddingValues
        )
    }
}
