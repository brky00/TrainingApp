package com.trainingappMob.myapplicationtraining.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.trainingappMob.myapplicationtraining.Meal

object FirestoreRepo {

    private fun firestore() = FirebaseFirestore.getInstance()

    // Function to register meal
    fun addMeal(meal: Meal, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore().collection("meals")
            .add(meal)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // Function to retrieve meal
    fun getMeals(onSuccess: (List<Pair<String, Meal>>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore().collection("meals")
            .get()
            .addOnSuccessListener { result ->
                val meals = result.documents.mapNotNull { document ->
                    document.toObject(Meal::class.java)?.let { meal ->
                        document.id to meal
                    }
                }
                onSuccess(meals)
            }
            .addOnFailureListener { onFailure(it) }
    }

    // Function to update
    fun updateMeal(id: String, updatedMeal: Meal, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore().collection("meals").document(id)
            .set(updatedMeal)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // function to delete
    fun deleteMeal(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore().collection("meals").document(id)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
