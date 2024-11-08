package com.trainingappMob.myapplicationtraining

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.trainingappMob.myapplicationtraining.Meal

object FirestoreRepo {
    private val firestore = FirebaseFirestore.getInstance()

    // Function to register meal
    fun addMeal(meal: Meal, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("meals")
            .add(meal)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // Function to retrieve meal
    fun getMeals(onSuccess: (List<Meal>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("meals")
            .get()
            .addOnSuccessListener { result ->
                val meals = result.mapNotNull { it.toObject(Meal::class.java) }
                onSuccess(meals)
            }
            .addOnFailureListener{onFailure(it)}
    }
}
