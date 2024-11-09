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
import androidx.compose.ui.unit.dp
import com.trainingappMob.myapplicationtraining.model.Workout
import com.trainingappMob.myapplicationtraining.viewModel.WorkoutViewModel

@Composable
fun RegisterWorkout(viewModel: WorkoutViewModel) {
    var selectedExercise by remember { mutableStateOf("Push-ups") }
    var expanded by remember { mutableStateOf(false) }
    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var timeTaken by remember { mutableStateOf("") }

    val exercises = listOf("Push-ups", "Squats", "Lunges", "Plank", "Pull-ups")
    val workouts by viewModel.workouts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWorkouts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register Workout")

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

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextFieldWithLabel(label = "Reps", value = reps) { reps = it }
            TextFieldWithLabel(label = "Sets", value = sets) { sets = it }
            TextFieldWithLabel(label = "Calories", value = calories) { calories = it }
            TextFieldWithLabel(label = "Time (min)", value = timeTaken) { timeTaken = it }
        }

        Button(onClick = {
            val newWorkout = Workout(
                date = "2024-11-10",
                exercise = selectedExercise,
                reps = reps.toIntOrNull() ?: 0,
                sets = sets.toIntOrNull() ?: 0,
                caloriesBurned = calories.toIntOrNull() ?: 0,
                timeTaken = "$timeTaken min"
            )
            viewModel.addWorkout(newWorkout)
        }) {
            Text(text = "Register Workout")
        }

        Text(text = "Previous Workouts")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(workouts) { workout ->
                WorkoutCard(
                    workout,
                    onDelete = { workoutId -> viewModel.deleteWorkout(workoutId) }
                )
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
fun WorkoutCard(
    workout: Workout,
    onDelete: (String) -> Unit
) {
    Card(
        border = BorderStroke(1.dp, Color(0xFF6200EA)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBB86FC)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Exercise: ${workout.exercise}")
            Text(text = "Reps: ${workout.reps}, Sets: ${workout.sets}")
            Text(text = "Calories: ${workout.caloriesBurned}, Time: ${workout.timeTaken}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onDelete(workout.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            }
        }
    }
}
