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
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavigationBetweenPages()

                }
            }
        }
    }
}


@Composable
fun NavigationBetweenPages() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home_page") {
        composable(route = "home_page") {
            HomePage(navController)
        }
        composable(route = "meal_page") {
            RegisterMeal(navController)
        }

        composable(route = "workout_page") {
            RegisterWorkout()
        }

    }
}






@Preview(showBackground = true)
@Composable
fun TrainingPreview() {
    MyApplicationTrainingTheme {

        NavigationBetweenPages()



    }
}