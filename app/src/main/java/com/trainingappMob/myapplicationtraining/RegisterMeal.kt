package com.trainingappMob.myapplicationtraining

import android.util.EventLogTags.Description
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme
import com.trainingappMob.myapplicationtraining.Meal

@Composable
fun RegisterMeal(navController: NavHostController) {
    // Lage state variabler
    var mealName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var Calories by remember { mutableStateOf("") }
    var Protein by remember { mutableStateOf("") }

    // State to hold list of registered meals
    val mealList = remember { mutableStateListOf<Meal>() }

    // Firestore instance
    val firestore = Firebase.firestore

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
                text = "Register meal page",
                color = Color(0xFF6200EA),
                fontSize = 30.sp,
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

                    // Button for registering meal
                    Button(
                        onClick = { // Logic for registering
                            val newMeal = Meal(mealName, description, Calories, Protein)
                            FirestoreRepo.addMeal(
                                newMeal,
                                onSuccess = {
                                    mealName = ""
                                    description = ""
                                    Calories = ""
                                    Protein = ""

                                    // Update the table or retrieving data from the DB and read them in the table
                                    FirestoreRepo.getMeals(
                                        onSuccess = { meals ->
                                            mealList.clear()
                                            mealList.addAll(meals)
                                        },
                                        onFailure = { e -> e.printStackTrace()}
                                    )
                                },
                                onFailure = {e -> e.printStackTrace()}
                            )

                        },
                        modifier = Modifier.padding(vertical = 8.dp),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text("Register Meal!")
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

            // Table to show registered meals
            MealTable(mealList)

        }
    }

}

@Composable
fun MealTable(mealList: List<Meal>){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        // Table header
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Meal",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Description",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Calories",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Protein",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Divider(color = Color.Gray, thickness = 1.dp) // Divider for header

        // Table rows for each meal
        mealList.forEach { meal ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = meal.mealName, modifier = Modifier.weight(1f))
                Text(text = meal.description, modifier = Modifier.weight(1f))
                Text(text = meal.calories, modifier = Modifier.weight(1f))
                Text(text = meal.protein, modifier = Modifier.weight(1f))
            }
            Divider(color = Color.LightGray, thickness = 0.5.dp) // Divider for each row
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MealPagePreview() {
    MyApplicationTrainingTheme {

        val navController = rememberNavController()

        RegisterMeal(navController = navController)
    }
}


