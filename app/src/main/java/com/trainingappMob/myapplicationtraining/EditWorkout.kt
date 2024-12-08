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
fun EditWorkout(navController: NavHostController, workoutId: String) {
    val firestore = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    val exercises = listOf(
        "Bench Press" to 5,
        "Squats (with weights)" to 7,
        "Deadlifts" to 8,
        "Pull-Ups" to 9,
        "Push-Ups" to 8,
        "Dumbbell Bicep Curls" to 4,
        "Dumbbell Tricep Kickbacks" to 4,
        "Barbell Rows" to 7,
        "Overhead Press (Shoulder Press)" to 7,
        "Lunges (with weights)" to 8,
        "Lat Pulldown" to 6,
        "Chest Fly (Machine or Dumbbell)" to 4,
        "Cable Rows" to 7,
        "Leg Press (Machine)" to 5,
        "Calf Raises (with weights)" to 4,
        "Plank (Weighted or Bodyweight)" to 4,
        "Side Plank (Weighted or Bodyweight)" to 3,
        "Kettlebell Swings" to 13,
        "Russian Twists (with weights)" to 7,
        "Romanian Deadlifts (RDLs)" to 7,
        "Running on Treadmill (6-8 km/h)" to 11,
        "Cycling (Stationary, Moderate)" to 9,
        "Rowing Machine (Moderate)" to 10,
        "Stair Climber" to 10,
        "Jump Rope (Moderate Pace)" to 13,
        "Burpees" to 13,
        "Elliptical Trainer (Moderate)" to 9,
        "HIIT (High Intensity Interval Training)" to 15,
        "Battle Ropes" to 11,
        "Box Jumps" to 13
    )

    var selectedExercise by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var totalMinutes by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    // Load workout data
    LaunchedEffect(workoutId) {
        if (workoutId.isNotEmpty()) {
            try {
                val workoutDoc = firestore.collection("RecordedWorkouts").document(workoutId).get().await()
                selectedExercise = workoutDoc.getString("workoutName") ?: ""
                reps = workoutDoc.getLong("reps")?.toString() ?: ""
                sets = workoutDoc.getLong("sets")?.toString() ?: ""
                totalMinutes = workoutDoc.getLong("totalMinutes")?.toString() ?: ""
                loading = false
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading workout: ${e.message}", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        } else {
            Toast.makeText(context, "Invalid Workout ID.", Toast.LENGTH_SHORT).show()
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
                text = "Edit Workout",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input fields
            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {
                // Button to trigger dropdown menu
                Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Choose Exercise: $selectedExercise")
                }

                // DropdownMenu to display exercise options
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    exercises.forEach { (name, _) ->
                        DropdownMenuItem(
                            text = { Text(text = name) },
                            onClick = {
                                selectedExercise = name
                                expanded = false // Closes the dropdown after selection
                            }
                        )
                    }
                }
            }

            TextField(
                value = reps,
                onValueChange = { reps = it },
                label = { Text("Reps") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            TextField(
                value = sets,
                onValueChange = { sets = it },
                label = { Text("Sets") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            TextField(
                value = totalMinutes,
                onValueChange = { totalMinutes = it },
                label = { Text("Total Minutes") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Save changes button
            Button(
                onClick = {
                    try {
                        val caloriesPerMinute = exercises.find { it.first == selectedExercise }?.second ?: 0
                        val averageTotalCaloriesBurned = totalMinutes.toIntOrNull()?.times(caloriesPerMinute) ?: 0
                        val updatedWorkout = mapOf(
                            "workoutName" to selectedExercise,
                            "reps" to reps.toIntOrNull(),
                            "sets" to sets.toIntOrNull(),
                            "totalMinutes" to totalMinutes.toIntOrNull(),
                            "averageTotalCaloriesBurned" to averageTotalCaloriesBurned
                        )
                        firestore.collection("RecordedWorkouts").document(workoutId)
                            .update(updatedWorkout)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Workout updated successfully!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Failed to update workout: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error updating workout: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}
