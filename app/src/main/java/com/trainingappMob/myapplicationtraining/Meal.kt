package com.trainingappMob.myapplicationtraining

import BottomNavBar
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

@Composable
fun Meal(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser
    val context = LocalContext.current

    // State variables
    var mealName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var recordedMeals by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

    // Load the last 7 meals
    LaunchedEffect(currentUser) {
        try {
            if (currentUser != null) {
                val meals = firestore.collection("RecordedMeals")
                    .whereEqualTo("userId", currentUser.uid)
                    .orderBy("recordDate", Query.Direction.DESCENDING)
                    .limit(7) // limitToLast fjernet for bedre indeksstøtte
                    .get()
                    .await()
                    .documents
                    .map { it.data ?: emptyMap() }
                recordedMeals = meals
            } else {
                Toast.makeText(context, "User not logged in. Please log in.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error loading meals: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Register your meal for now",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input fields
            TextField(
                value = mealName,
                onValueChange = { mealName = it },
                label = { Text("Meal Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = calories,
                onValueChange = { calories = it },
                label = { Text("Calories") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = protein,
                onValueChange = { protein = it },
                label = { Text("Protein") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )


            // Save meal button
            Button(
                onClick = {
                    try {
                        if (currentUser != null) {
                            val meal = hashMapOf(
                                "mealName" to mealName,
                                "calories" to calories.toIntOrNull(),
                                "protein" to protein.toIntOrNull(),
                                "recordDate" to com.google.firebase.Timestamp.now(),
                                "userId" to currentUser.uid
                            )
                            firestore.collection("RecordedMeals").add(meal)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Meal successfully saved", Toast.LENGTH_SHORT).show()

                                    // Updating total score
                                    firestore.collection("users").document(currentUser.uid)
                                        .get()
                                        .addOnSuccessListener { document ->
                                            val currentScore = document.getLong("totalScore") ?: 0
                                            firestore.collection("users").document(currentUser.uid)
                                                .update("totalScore", currentScore + 2)
                                                .addOnSuccessListener {
                                                    Toast.makeText(
                                                        context,
                                                        "Total score updated successfully",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                        }

                                    // Reload the meals list
                                    firestore.collection("RecordedMeals")
                                        .whereEqualTo("userId", currentUser.uid)
                                        .orderBy("recordDate", Query.Direction.DESCENDING)
                                        .limit(7)
                                        .get()
                                        .addOnSuccessListener { snapshot ->
                                            recordedMeals = snapshot.documents.map { it.data ?: emptyMap() }
                                        }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Failed to save meal: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(context, "User not logged in. Please log in.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error saving meal: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save the Meal")
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Last 7 meals list
            Text(
                text = "Your Last 7 Recorded Meals",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(recordedMeals) { meal ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Meal Name: ${meal["mealName"] ?: "Unknown"}")
                        Text(text = "Calories: ${meal["calories"] ?: "Unknown"}")
                        Text(text = "Protein: ${meal["protein"] ?: "Unknown"}")

                        val timestamp = meal["recordDate"] as? com.google.firebase.Timestamp
                        val formattedDate = timestamp?.toDate()?.let { date ->
                            java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(date)
                        } ?: "Unknown date"
                        Text(text = "Date: $formattedDate")
                    }
                }
            }
        }
    }
}
