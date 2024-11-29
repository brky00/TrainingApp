package com.trainingappMob.myapplicationtraining

import BottomNavBar
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

@Composable
fun Workout(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser
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

    var selectedExercise by remember { mutableStateOf(exercises.first().first) }
    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var totalMinutes by remember { mutableStateOf("") }
    var recordedWorkouts by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

    // Loading the last 7 workouts
    LaunchedEffect(currentUser) {
        try {
            if (currentUser != null) {
                val workouts = firestore.collection("RecordedWorkouts")
                    .whereEqualTo("userId", currentUser.uid)
                    .orderBy("workoutDate", Query.Direction.DESCENDING)
                    .limit(7)
                    .get()
                    .await()
                    .documents
                    .map { doc -> doc.data?.plus("id" to doc.id) ?: emptyMap() }
                recordedWorkouts = workouts
            } else {
                Toast.makeText(context, "User not logged in. Please log in.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error loading workouts: ${e.message}", Toast.LENGTH_SHORT).show()
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
                text = "Register Your Workout",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 14.dp)
            )
            Text(
                text = "Choose a exercise here please",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )

            // Input fields

            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {
                // Button to trigger dropdown menu
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Choosen Exercise: $selectedExercise",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(end = 8.dp) // Litt mellomrom mellom tekst og ikon
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown, // Bruker Material Design ArrowDropDown ikon
                            contentDescription = "Dropdown Icon"
                        )
                    }
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
                                expanded = false // Lukk dropdown etter valg
                            }
                        )
                    }
                }
            }




            // Input fields in box format
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Reps input
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it },
                    label = { Text("Reps") },
                    singleLine = true,
                    modifier = Modifier
                        .width(105.dp) // Adjust width
                        .height(75.dp) // Adjust height
                        .padding(horizontal = 6.dp),
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                // Sets input
                OutlinedTextField(
                    value = sets,
                    onValueChange = { sets = it },
                    label = { Text("Sets") },
                    singleLine = true,
                    modifier = Modifier
                        .width(105.dp) // Adjust width
                        .height(75.dp) // Adjust height
                        .padding(horizontal = 6.dp),
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                // Total Minutes input
                OutlinedTextField(
                    value = totalMinutes,
                    onValueChange = { totalMinutes = it },
                    label = { Text("Minutes") },
                    singleLine = true,
                    modifier = Modifier
                        .width(105.dp) // Adjust width
                        .height(75.dp) // Adjust height
                        .padding(horizontal = 6.dp),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }

            // Register workout button
            Button(
                onClick = {
                    try {
                        if (currentUser != null) {
                            val caloriesPerMinute = exercises.find { it.first == selectedExercise }?.second ?: 0
                            val averageTotalCaloriesBurned = totalMinutes.toIntOrNull()?.times(caloriesPerMinute) ?: 0
                            val workout = hashMapOf(
                                "workoutName" to selectedExercise,
                                "reps" to reps.toIntOrNull(),
                                "sets" to sets.toIntOrNull(),
                                "totalMinutes" to totalMinutes.toIntOrNull(),
                                "averageTotalCaloriesBurned" to averageTotalCaloriesBurned,
                                "workoutDate" to Timestamp.now(),
                                "userId" to currentUser.uid
                            )
                            firestore.collection("RecordedWorkouts").add(workout)
                                .addOnSuccessListener {
                                    val scoreIncrement = if (averageTotalCaloriesBurned > 700) 3 else 2
                                    updateUserScore(firestore, currentUser.uid, scoreIncrement, context)
                                    Toast.makeText(
                                        context,
                                        "Workout successfully registered! You gained +$scoreIncrement score.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Oppdater recordedWorkouts umiddelbart etter vellykket registrering
                                    firestore.collection("RecordedWorkouts")
                                        .whereEqualTo("userId", currentUser.uid)
                                        .orderBy("workoutDate", Query.Direction.DESCENDING)
                                        .limit(7)
                                        .get()
                                        .addOnSuccessListener { snapshot ->
                                            recordedWorkouts = snapshot.documents.map { it.data?.plus("id" to it.id) ?: emptyMap() }
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Failed to refresh workouts: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Failed to save workout: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(context, "User not logged in. Please log in.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error saving workout: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register Workout")
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Last 7 workouts list
            Text(
                text = "Your Last 7 Recorded Workouts",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(recordedWorkouts) { workout ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)

                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = "Workout: ${workout["workoutName"]}")
                            Text(text = "Reps: ${workout["reps"]}")
                            Text(text = "Sets: ${workout["sets"]}")
                            Text(text = "Calories Burned: ${workout["averageTotalCaloriesBurned"]}")
                            Text(text = "Total Minutes: ${workout["totalMinutes"]}")

                            // Formatering og visning av workoutDate
                            val timestamp = workout["workoutDate"] as? Timestamp
                            val formattedDate = timestamp?.toDate()?.let { date ->
                                java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(date)
                            } ?: "Unknown date"
                            Text(
                                text = "Date: $formattedDate",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                            )

                            // Edit and Delete Buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = {
                                    val workoutId = workout["id"] as? String
                                    if (!workoutId.isNullOrEmpty()) {
                                        navController.navigate("edit_workout_page/$workoutId")
                                    }
                                }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Workout")
                                }

                                IconButton(onClick = {
                                    val workoutId = workout["id"] as? String
                                    if (!workoutId.isNullOrEmpty()) {
                                        deleteWorkoutAndUpdateScore(
                                            firestore = firestore,
                                            workoutId = workoutId,
                                            userId = currentUser?.uid ?: "",
                                            context = context,
                                            onSuccess = {
                                                recordedWorkouts = recordedWorkouts.filterNot { it["id"] == workoutId }
                                            }
                                        )
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Workout",
                                        tint = MaterialTheme.colorScheme.error
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


fun updateUserScore(
    firestore: FirebaseFirestore,
    userId: String,
    increment: Int,
    context: Context
) {
    firestore.collection("users").document(userId)
        .get()
        .addOnSuccessListener { document ->
            val currentScore = document.getLong("totalScore") ?: 0
            val updatedScore = currentScore + increment
            firestore.collection("users").document(userId)
                .update("totalScore", updatedScore)
                .addOnSuccessListener {
                    Toast.makeText(context, "Total score updated successfully!", Toast.LENGTH_SHORT).show()
                }
        }
}

fun deleteWorkoutAndUpdateScore(
    firestore: FirebaseFirestore,
    workoutId: String,
    userId: String,
    context: Context,
    onSuccess: () -> Unit
) {
    try {
        // Hent detaljene om treningsøkten som skal slettes
        firestore.collection("RecordedWorkouts").document(workoutId)
            .get()
            .addOnSuccessListener { document ->
                val caloriesBurned = document.getLong("averageTotalCaloriesBurned") ?: 0

                // Slett treningsøkten
                firestore.collection("RecordedWorkouts").document(workoutId)
                    .delete()
                    .addOnSuccessListener {
                        // Oppdater brukerens totalScore
                        firestore.collection("users").document(userId)
                            .get()
                            .addOnSuccessListener { userDocument ->
                                val currentScore = userDocument.getLong("totalScore") ?: 0
                                val scoreReduction = if (caloriesBurned > 700) 3 else 2
                                val updatedScore = maxOf(0, currentScore - scoreReduction) // Unngå negative poeng
                                firestore.collection("users").document(userId)
                                    .update("totalScore", updatedScore)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            context,
                                            "Workout deleted and score updated with -$scoreReduction!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        onSuccess()
                                    }
                            }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Failed to delete workout: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error retrieving workout details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    } catch (e: Exception) {
        Toast.makeText(context, "Error deleting workout: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

