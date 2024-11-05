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
import com.trainingappMob.myapplicationtraining.backend.googleSignIn.GoogleAuthClient
import com.trainingappMob.myapplicationtraining.repository.MealRepository
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme
import com.trainingappMob.myapplicationtraining.viewService.MealViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val googleAuthClient = GoogleAuthClient(applicationContext)

        setContent {
            MyApplicationTrainingTheme {
                val navController = rememberNavController()
                var isSignIn by rememberSaveable {
                    mutableStateOf(googleAuthClient.isSingedIn())
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { paddingValues ->
                    NavigationBetweenPages(
                        navController = navController,
                        paddingValues = paddingValues,
                        isSignIn = isSignIn,
                        googleAuthClient = googleAuthClient,
                        onSignInClick = {
                            lifecycleScope.launch {
                                isSignIn = googleAuthClient.signIn(this@MainActivity)
                            }
                        }
,
                        onSignOutClick = {
                            lifecycleScope.launch {
                                googleAuthClient.signOut()
                                isSignIn = false
                            }
                        }
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
    isSignIn: Boolean,
    googleAuthClient: GoogleAuthClient,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    val mealViewModel = MealViewModel(repository = MealRepository())

    NavHost(
        navController = navController,
        startDestination = "signIn",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = "home_page") {
            HomePage(navController, viewModel = mealViewModel)
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
        composable(route = "navbar") {
            BottomNavBar(navController)
        }
        composable(route = "signIn") {
            GoogleSignInButton(
                navController = navController,
                isSignIn = isSignIn,
                googleAuthClient = googleAuthClient,
                onSignInClick = onSignInClick,
                onSignOutClick = onSignOutClick
            )
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
            isSignIn = TODO(),
            onSignInClick = TODO(),
            onSignOutClick = TODO(),
            googleAuthClient = TODO(),
        )
    }
}
