package com.trainingappMob.myapplicationtraining.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.trainingappMob.myapplicationtraining.model.Workout
import kotlinx.coroutines.tasks.await

class WorkoutRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val workoutsCollection = firestore.collection("Workouts")

    suspend fun getWorkouts(): List<Workout> {
        return workoutsCollection
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents
            .map { document ->
                document.toObject(Workout::class.java)?.apply {
                    id = document.id
                }
            }.filterNotNull()
    }

    suspend fun addWorkout(workout: Workout) {
        workoutsCollection.add(workout).await()
    }

    suspend fun updateWorkout(workout: Workout) {
        workoutsCollection.document(workout.id).set(workout).await()
    }

    suspend fun deleteWorkout(workoutId: String) {
        workoutsCollection.document(workoutId).delete().await()
    }
}

