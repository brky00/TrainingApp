package com.trainingappMob.myapplicationtraining
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


@Composable
fun EditMeal(navController: NavHostController, mealId: String) {
    val firestore = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var mealName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    // Load meal data
    LaunchedEffect(mealId) {
        if (mealId.isNotEmpty()) {
            try {
                val mealDoc = firestore.collection("RecordedMeals").document(mealId).get().await()
                mealName = mealDoc.getString("mealName") ?: ""
                calories = mealDoc.getLong("calories")?.toString() ?: ""
                protein = mealDoc.getLong("protein")?.toString() ?: ""
                loading = false
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading meal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Invalid Meal ID.", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }


    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Meal",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input fields
            TextField(
                value = mealName,
                onValueChange = { mealName = it },
                label = { Text("Meal Name") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            TextField(
                value = calories,
                onValueChange = { calories = it },
                label = { Text("Calories") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            TextField(
                value = protein,
                onValueChange = { protein = it },
                label = { Text("Protein") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Save changes button
            Button(
                onClick = {
                    try {
                        val updatedMeal = mapOf(
                            "mealName" to mealName,
                            "calories" to calories.toIntOrNull(),
                            "protein" to protein.toIntOrNull()
                        )
                        firestore.collection("RecordedMeals").document(mealId)
                            .update(updatedMeal)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Meal updated successfully!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack() // Navigate back to the previous page
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Failed to update meal: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error updating meal: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save Changes")
            }
        }
    }
}
