package com.trainingappMob.myapplicationtraining.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.trainingappMob.myapplicationtraining.model.Workout
import kotlinx.coroutines.tasks.await

class WorkoutRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val workoutsCollection = firestore.collection("Workouts")

    suspend fun getWorkouts(): List<Workout> {
        return workoutsCollection
            .get()
            .await()
            .documents
            .map { document ->
                document.toObject(Workout::class.java)?.apply {
                    id = document.id  // Setter dokument-ID
                }
            }.filterNotNull()
    }

    suspend fun addWorkout(workout: Workout) {
        workoutsCollection.add(workout).await()
    }

    suspend fun deleteWorkout(workoutId: String) {
        workoutsCollection.document(workoutId).delete().await()
    }
}
