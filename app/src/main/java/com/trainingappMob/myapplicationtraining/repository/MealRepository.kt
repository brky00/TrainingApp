package com.trainingappMob.myapplicationtraining.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.trainingappMob.myapplicationtraining.model.Meal
import kotlinx.coroutines.tasks.await

public open class MealRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val mealsCollection = firestore.collection("Meals")

    open suspend fun getMealsForBuildMuscle(): List<Meal> {
        return mealsCollection
            .whereGreaterThanOrEqualTo("protein", 18)
            .limit(4)
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
    }

    open suspend fun getMealsForLoseWeight(): List<Meal> {
        return mealsCollection
            .whereLessThanOrEqualTo("calories", 150)
            .limit(4)
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
    }
}