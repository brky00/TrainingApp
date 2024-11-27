// MainActivity.kt
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
import com.trainingappMob.myapplicationtraining.repository.MealRepository
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme
import com.trainingappMob.myapplicationtraining.viewService.MealViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTrainingTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),

                ) { paddingValues ->
                    NavigationBetweenPages(
                        navController = navController,
                        paddingValues = paddingValues,
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationBetweenPages(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val mealViewModel = MealViewModel(repository = MealRepository())

    NavHost(
        navController = navController,
        startDestination = "welcome_page",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = "welcome_page") {
            WelcomeScreen(navController = navController)
        }
        composable(route = "login_page") {
            LoginPage(navController = navController)
        }
        composable(route = "register_page") {
            RegisterPage(navController = navController)
        }
        composable(route = "home_page") {
            HomePage(navController, viewModel = mealViewModel)
        }
        composable(route = "meal_page") {
            Meal(navController)
        }
        composable(route = "workout_page") {
            RegisterWorkout(navController)
        }
        composable(route = "profil_page") {
            ProfilPage(navController)
        }
        composable(route = "edit_profile_page") {
            EditProfilePage(navController = navController)
        }
        composable(route = "edit_meal_page/{mealId}") { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            EditMeal(navController = navController, mealId = mealId)
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
            paddingValues = paddingValues,
        )
    }
}
