package com.trainingappMob.myapplicationtraining.model

data class Workout(
    var id: String = "",
    val date: String = "",
    val exercise: String = "",
    val reps: Int = 0,
    val sets: Int = 0,
    val caloriesBurned: Int = 0,
    val timeTaken: String = ""
)

