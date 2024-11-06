package com.trainingappMob.myapplicationtraining

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegisterWorkout() {
    var selectedExercise by remember { mutableStateOf("Push-ups") }
    var expanded by remember { mutableStateOf(false) }
    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var timeTaken by remember { mutableStateOf("") }

    val exercises = listOf("Push-ups", "Squats", "Lunges", "Plank", "Pull-ups")

    // Sample data for last week's workouts
    val workoutsLastWeek = listOf(
        Workout("2024-10-20", "Push-ups", 20, 4, 100, "15 min"),
        Workout("2024-10-21", "Squats", 15, 3, 120, "20 min"),
        Workout("2024-10-22", "Lunges", 12, 4, 150, "25 min"),
        Workout("2024-10-23", "Plank", 1, 3, 50, "5 min"),
        Workout("2024-10-24", "Pull-ups", 10, 3, 80, "10 min")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register Workout")

        // Exercise Selection with DropdownMenu
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "Exercise:")
            Box {
                Text(
                    text = selectedExercise,
                    modifier = Modifier
                        .clickable { expanded = true }
                        .padding(8.dp)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    exercises.forEach { exercise ->
                        DropdownMenuItem(
                            onClick = {
                                selectedExercise = exercise
                                expanded = false
                            },
                            text = { Text(text = exercise) }
                        )
                    }
                }
            }
        }

        // Input fields for workout details
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextFieldWithLabel(label = "Reps", value = reps) { reps = it }
            TextFieldWithLabel(label = "Sets", value = sets) { sets = it }
            TextFieldWithLabel(label = "Calories", value = calories) { calories = it }
            TextFieldWithLabel(label = "Time (min)", value = timeTaken) { timeTaken = it }
        }

        // Submit button
        Button(onClick = {
            // Handle workout registration (data submission logic goes here)
        }) {
            Text(text = "Register Workout")
        }

        // Displaying workouts from the last week
        Text(text = "Previous Workouts (Last Week)")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(workoutsLastWeek) { workout ->
                WorkoutCard(workout)
            }
        }
    }
}

@Composable
fun TextFieldWithLabel(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.width(80.dp)
        )
    }
}

@Composable
fun WorkoutCard(workout: Workout) {
    Card(
        border = BorderStroke(1.dp, Color(0xFF6200EA)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBB86FC)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Exercise: ${workout.exercise}")
            Text(text = "Reps: ${workout.reps}, Sets: ${workout.sets}")
            Text(text = "Calories: ${workout.caloriesBurned}, Time: ${workout.timeTaken}")
        }
    }
}

data class Workout(
    val date: String,
    val exercise: String,
    val reps: Int,
    val sets: Int,
    val caloriesBurned: Int,
    val timeTaken: String
)

@Preview(showBackground = true)
@Composable
fun PreviewRegisterWorkout() {
    RegisterWorkout()
}
