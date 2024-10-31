package com.trainingappMob.myapplicationtraining

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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme

@Composable
fun RegisterMeal(navController: NavHostController) {
    // Lage state variabler
    var mealName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var Calories by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.meal3),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .graphicsLayer(alpha = 0.5f)
        )
    }

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
                .padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))

        // Asking user to enter fields.
        Text(
            text = "Enter your Meal name and meal description:",
            color = Color(0xFF6200EA),
            modifier = Modifier.padding(vertical = 8.dp)
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
            modifier = Modifier.padding(vertical = 8.dp)
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
            modifier = Modifier.padding(vertical = 8.dp),

        )

        // Button for registering meal
        Button(
            onClick = {/* Logic for registering meal */ },
            modifier = Modifier.padding(vertical = 16.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text("Register Meal!")
        }

        Button(onClick = {navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Go back"
            )
            Text("Go back")
        }
        Column (verticalArrangement = Arrangement.Center){
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.size(width = 240.dp, height = 100.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Piece of Information",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
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


