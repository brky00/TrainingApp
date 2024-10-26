package com.trainingappMob.myapplicationtraining


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTrainingTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {

                        BottomNavBar(navController = navController)
                    } ) {
                    NavigationBetweenPages(navController)

                }
            }
        }
    }
}


@Composable
fun NavigationBetweenPages(navController: NavHostController) {
    

    NavHost(navController = navController, startDestination = "home_page") {
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

        NavigationBetweenPages(
            navController = TODO()
        )



    }
}