import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.trainingappMob.myapplicationtraining.HomePage
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme

@Composable
fun BottomNavBar(navController: NavController) {
    //  list of navbar items
    val items = listOf(
        NavItem("home_page", Icons.Default.Home, "Home"),
        NavItem("workout_page", Icons.Default.FitnessCenter, "Workout"),
        NavItem("meal_page", Icons.Default.Restaurant, "Meals"),
        NavItem("profil_page", Icons.Default.Person, "Profile")
    )

    //  vurrent route catching here
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // BottomAppBar
    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { item ->

                IconButton(
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },

                ) {
                    // Icon in navbar
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) Color.Blue else Color.Gray
                    )
                }
            }
        }
    }
}

//  data class
data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    MyApplicationTrainingTheme {

        val navController = rememberNavController()


        BottomNavBar(navController = navController)
    }
}
