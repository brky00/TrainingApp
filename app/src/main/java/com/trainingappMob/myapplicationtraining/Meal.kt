package com.trainingappMob.myapplicationtraining

import BottomNavBar
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
                    .limit(7)
                    .get()
                    .await()
                    .documents
                    .map { doc ->
                        // adding document-ID as "id"
                        doc.data?.plus("id" to doc.id) ?: emptyMap()
                    }
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

                                    // puts fields to empity again
                                    mealName = ""
                                    calories = ""
                                    protein = ""

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
                                            recordedMeals = snapshot.documents.map { it.data?.plus("id" to it.id) ?: emptyMap() }
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
                    // Added Card for better styling
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Space between records
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(4.dp), // Shadow effect for better UI
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer // Light purple background
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp), // Inner padding for each record
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Meal information
                            Text(
                                text = "Meal Name: ${meal["mealName"] ?: "Unknown"}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Calories: ${meal["calories"] ?: "Unknown"}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Protein: ${meal["protein"] ?: "Unknown"}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            val timestamp = meal["recordDate"] as? com.google.firebase.Timestamp
                            val formattedDate = timestamp?.toDate()?.let { date ->
                                java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(date)
                            } ?: "Unknown date"
                            Text(
                                text = "Date: $formattedDate",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                            )

                            // Row for Edit and Delete Icons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End // Align icons to the right
                            ) {

                                // Edit Icon
                                IconButton(onClick = {
                                    val mealId = meal["id"] as? String
                                    if (!mealId.isNullOrEmpty()) {
                                        navController.navigate("edit_meal_page/$mealId")
                                    } else {
                                        Toast.makeText(context, "Meal ID is missing or invalid", Toast.LENGTH_SHORT).show()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Meal",
                                        tint = MaterialTheme.colorScheme.primary // Default primary color for edit
                                    )
                                }
                                // Delete Icon
                                IconButton(onClick = {
                                    meal["id"]?.let { mealId ->
                                        deleteMealAndUpdateScore(
                                            firestore = firestore,
                                            mealId = mealId as String,
                                            userId = currentUser?.uid ?: "",
                                            context = context,
                                            onSuccess = {
                                                recordedMeals = recordedMeals.filterNot { it["id"] == mealId }
                                            }
                                        )
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Meal",
                                        modifier = Modifier.padding(end = 8.dp), // Space between icons
                                        tint = MaterialTheme.colorScheme.error // Red color for delete
                                    )
                                }


                            }
                        }
                    }
                }
            }

        }
    }
}

fun deleteMealAndUpdateScore(
    firestore: FirebaseFirestore,
    mealId: String,
    userId: String,
    context: Context,
    onSuccess: () -> Unit
) {
    try {
        firestore.collection("RecordedMeals").document(mealId)
            .delete()
            .addOnSuccessListener {
                // Update the user's totalScore
                firestore.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener { document ->
                        val currentScore = document.getLong("totalScore") ?: 0
                        val updatedScore = maxOf(0, currentScore - 2) // Prevent negative scores
                        firestore.collection("users").document(userId)
                            .update("totalScore", updatedScore)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Meal deleted and score updated with -2!", Toast.LENGTH_SHORT).show()
                                onSuccess()
                            }
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to delete meal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    } catch (e: Exception) {
        Toast.makeText(context, "Error deleting meal: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}
