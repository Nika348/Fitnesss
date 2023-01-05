package com.example.fitnesss.models.library

import com.example.fitnesss.models.api_model.IdealWeightModel
import com.example.fitnesss.models.api_model.IndexModel
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.workouts.Workouts
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    val favoritesWorkout: Flow<List<Workouts>>

    suspend fun getLibrary(): List<Library>
    suspend fun getWorkouts(libraryId: Int): List<Workouts>
    suspend fun getDetail(workoutId: Int): Detail

    suspend fun updateFavoriteStatus(workoutId: Int, isFavorite: Boolean)

    suspend fun getIndexModel(age: Int, height: Int, weight: Int): IndexModel
    suspend fun getIdealWeightModel(gender: String, height: Int): IdealWeightModel
}