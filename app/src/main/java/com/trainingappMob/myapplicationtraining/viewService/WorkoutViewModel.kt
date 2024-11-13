package com.trainingappMob.myapplicationtraining.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainingappMob.myapplicationtraining.model.Workout
import com.trainingappMob.myapplicationtraining.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {

    private val workoutRepository = WorkoutRepository()

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    fun loadWorkouts() {
        viewModelScope.launch {
            _workouts.value = workoutRepository.getWorkouts()
        }
    }

    fun addWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.addWorkout(workout)
            loadWorkouts()
        }
    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.updateWorkout(workout)
            loadWorkouts()
        }
    }

    fun deleteWorkout(workoutId: String) {
        viewModelScope.launch {
            workoutRepository.deleteWorkout(workoutId)
            loadWorkouts()
        }
    }
}

