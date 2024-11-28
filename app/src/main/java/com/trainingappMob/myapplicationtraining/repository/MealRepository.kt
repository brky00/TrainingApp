package com.trainingappMob.myapplicationtraining.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.trainingappMob.myapplicationtraining.model.Meal
import kotlinx.coroutines.tasks.await
//Meal Repository
public open class MealRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val mealsCollection = firestore.collection("Meals")

    // Get 4 random meals for building muscle
    suspend fun getMealsForBuildMuscle(): List<Meal> {
        val allMeals = mealsCollection
            .whereGreaterThanOrEqualTo("protein", 18)
            .get()
            .await()
            .documents
            .map { document ->
                Meal(
                    name = document.getString("name") ?: "",
                    calories = document.getLong("calories")?.toInt() ?: 0,
                    protein = document.getLong("protein")?.toInt() ?: 0,
                    imageName = document.getString("imageName") ?: ""
                )
            }
        return allMeals.shuffled().take(4) // Shuffle and select 4 random meals
    }

    // Get 4 random meals for losing weight
    suspend fun getMealsForLoseWeight(): List<Meal> {
        val allMeals = mealsCollection
            .whereLessThanOrEqualTo("calories", 150)
            .get()
            .await()
            .documents
            .map { document ->
                Meal(
                    name = document.getString("name") ?: "",
                    calories = document.getLong("calories")?.toInt() ?: 0,
                    protein = document.getLong("protein")?.toInt() ?: 0,
                    imageName = document.getString("imageName") ?: ""
                )
            }
        return allMeals.shuffled().take(4) // Shuffle and select 4 random meals
    }
}