package com.trainingappMob.myapplicationtraining.viewService

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainingappMob.myapplicationtraining.model.Meal
import com.trainingappMob.myapplicationtraining.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealViewModel(private val repository: MealRepository) : ViewModel() {

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals = _meals.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun loadMeals(goal: String) {
        viewModelScope.launch {
            _loading.value = true
            _meals.value = if (goal == "build muscle") {
                repository.getMealsForBuildMuscle()
            } else {
                repository.getMealsForLoseWeight()
            }
            _loading.value = false
        }
    }
}