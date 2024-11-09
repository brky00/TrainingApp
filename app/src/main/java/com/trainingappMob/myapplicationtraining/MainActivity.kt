package com.trainingappMob.myapplicationtraining


import BottomNavBar
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trainingappMob.myapplicationtraining.repository.MealRepository
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme
import com.trainingappMob.myapplicationtraining.viewModel.WorkoutViewModel
import com.trainingappMob.myapplicationtraining.viewService.MealViewModel
import kotlinx.coroutines.launch

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
                    bottomBar = { BottomNavBar(navController = navController) }
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
    // Opprett instanser av både MealViewModel og WorkoutViewModel
    val mealViewModel = MealViewModel(repository = MealRepository())
    val workoutViewModel = WorkoutViewModel() // Opprett WorkoutViewModel her

    NavHost(
        navController = navController,
        startDestination = "home_page",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = "home_page") {
            HomePage(navController, viewModel = mealViewModel)
        }
        composable(route = "meal_page") {
            RegisterMeal(navController)
        }
        composable(route = "workout_page") {
            RegisterWorkout(viewModel = workoutViewModel) // Send WorkoutViewModel her
        }
        composable(route = "profil_page") {
            ProfilPage(navController)
        }
        composable(route = "navbar") {
            BottomNavBar(navController)
        }
        composable(route = "welcome_page") {
            WelcomeScreen(navController = navController)
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
