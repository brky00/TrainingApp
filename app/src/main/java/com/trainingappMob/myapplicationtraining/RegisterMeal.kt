package com.trainingappMob.myapplicationtraining

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.trainingappMob.myapplicationtraining.repository.MealRepository
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme
import com.trainingappMob.myapplicationtraining.viewService.MealViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterMeal(navController: NavHostController) {

    // Making in app messages
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Making state variables
    var mealName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var Calories by remember { mutableStateOf("") }
    var Protein by remember { mutableStateOf("") }

    // State to hold list of registered meals
    val mealList = remember { mutableStateListOf<Pair<String, Meal>>() }

    // Variable that tracks meal being edited
    var mealToEdit by remember { mutableStateOf<String?>(null) }

    // Set up real time listener for meals collection
    // Launchedeffect. loading the registered meals and display them
    LaunchedEffect(Unit) {
        FirestoreRepo.getMeals(
            onSuccess =  { meals ->
                mealList.clear()
                mealList.addAll(meals)
            },
            onFailure = { e ->
                e.printStackTrace() // Error handling
            }
        )
    }

    // Show only the last 7 meals to the table
    val last7Meals = mealList.takeLast(7)

    // Show snackbar message
    fun showSnackBar(message: String) {
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    // Function to handle update
    fun handleUpdate(mealID: String, meal: Meal) {
        mealToEdit = mealID
        mealName = meal.mealName
        description = meal.description
        Calories = meal.calories
        Protein = meal.protein
    }

    // funtion to delete
    fun handleDelete(mealID: String) {
        FirestoreRepo.deleteMeal(mealID, onSuccess = {
            mealList.removeAll { it.first == mealID }
            showSnackBar("Meal is deleted!")
        }, onFailure = { e ->
            e.printStackTrace()
            showSnackBar("Failed to delete")
        })
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.meal3),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .graphicsLayer(alpha = 0.5f)
        )

        // Main content
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Register meal. TITLE
            Text(
                text = if (mealToEdit == null) "Register meal page" else "Edit meal",
                color = Color(0xFF6200EA),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))

            // Content card with text fields inside
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Asking user to enter fields.
                    Text(
                        text = "Enter your Meal name and meal description:",
                        color = Color(0xFF6200EA),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Meal name label
                    Text(
                        text = "Meal Name:",
                        color = Color(0xFF6200EA),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    // Meal name input
                    TextField(
                        value = mealName,
                        onValueChange = {mealName = it},
                        label = { Text("Enter Meal Name") },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Meal description label
                    Text(
                        text = "Description:",
                        color = Color(0xFF6200EA),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    // Meal description input
                    TextField(
                        value = description,
                        onValueChange = {description = it},
                        label = { Text("Enter description") },
                        modifier = Modifier.padding(vertical = 4.dp),

                        )

                    // Calories label
                    Text(
                        text = "Enter calories:",
                        color = Color(0xFF6200EA),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Calories input
                    TextField(
                        value = Calories,
                        onValueChange = {Calories = it},
                        label = { Text("Enter calories") },
                        modifier = Modifier.padding(vertical = 4.dp),

                        )

                    // Protein label
                    Text(
                        text = "Enter Protein:",
                        color = Color(0xFF6200EA),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Protein input
                    TextField(
                        value = Protein,
                        onValueChange = {Protein = it},
                        label = { Text("Enter Protein") },
                        modifier = Modifier.padding(vertical = 4.dp),

                        )

                    // Button for registering meal or updating meal
                    Button(
                        onClick = { // Logic for registering
                            val meal = Meal(mealName, description, Calories, Protein)
                            if (mealToEdit == null) {

                                // Register new meal
                                FirestoreRepo.addMeal(meal, onSuccess = {
                                    FirestoreRepo.getMeals(
                                        onSuccess = { meals ->
                                            mealList.clear()
                                            mealList.addAll(meals)
                                        },
                                        onFailure = { e -> e.printStackTrace() }
                                    )
                                    mealName = ""
                                    description = ""
                                    Calories = ""
                                    Protein = ""
                                    showSnackBar("Meal is registered!")

                                }, onFailure = { e -> e.printStackTrace()
                                    showSnackBar("Failed to register")
                                })

                            } else {

                                // Updating an existing meal
                                FirestoreRepo.updateMeal(mealToEdit!!, meal, onSuccess = {

                                    FirestoreRepo.getMeals(
                                        onSuccess = { meals ->
                                            mealList.clear()
                                            mealList.addAll(meals)
                                        },
                                        onFailure =  { e -> e.printStackTrace() }
                                    )
                                    mealToEdit = null
                                    mealName = ""
                                    description = ""
                                    Calories = ""
                                    Protein = ""
                                    showSnackBar("Meal is updated!")
                                    // Error
                                }, onFailure = { e -> e.printStackTrace()
                                    showSnackBar("Failed to update")
                                })
                            }
                        },
                        modifier = Modifier.padding(vertical = 8.dp),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(if (mealToEdit == null) "Register Meal!" else "Update Meal")
                    }
                }
            }

            Button(onClick = {navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Go back"
                )
                Text("Go back")
            }

            Spacer(modifier = Modifier.size(16.dp))

            // Table to show data and function to delete and update
            MealTable(

                mealList = last7Meals,
                onUpdate = { id, meal -> handleUpdate(id, meal) },
                onDelete = { id -> handleDelete(id) }
            )

            Box(modifier = Modifier.fillMaxSize()) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

        }
    }

}

@Composable
fun MealTable(
    mealList: List<Pair<String, Meal>>,
    // adding inline options
    onUpdate: (String, Meal) -> Unit,
    onDelete: (String) -> Unit
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        // Adding scroll ability
        androidx.compose.foundation.rememberScrollState().let { scrollState ->
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                // Table header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(Color(0xFFEFEFEF)),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    //  Meal
                    Text(
                        text = "Meal",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    //  description
                    Text(
                        text = "Description",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1.5f)
                    )

                    // Calories
                    Text(
                        text = "Calories",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "Protein",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    // Actions
                    Text(
                        text = "Action",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(0.7f)
                    )
                }

                // Divider
                Divider(color = Color.Gray, thickness = 1.dp)

                // Table rows for each meal
                mealList.forEach { (id, meal) ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color(0xFFF9F9F9)),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // Meal name
                        Text(
                            text = meal.mealName,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )

                        // Description
                        Text(
                            text = meal.description,
                            modifier = Modifier.weight(1.5f),
                            textAlign = TextAlign.Start,
                            maxLines = 2
                        )

                        // Calories
                        Text(
                            text = meal.calories,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )

                        //  Protein
                        Text(
                            text = meal.protein,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )

                        // Action buttons
                        Row (
                            modifier = Modifier.weight(0.7f),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            // Update icon
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Update",
                                tint = Color.Blue,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { onUpdate(id, meal) }
                                    .padding(end = 8.dp)
                            )

                            // Delete icon
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Update",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { onDelete(id) }

                            )
                        }
                    }
                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealPagePreview() {
    MyApplicationTrainingTheme {


        val navController = rememberNavController()


        RegisterMeal(navController = navController,)
    }
}


