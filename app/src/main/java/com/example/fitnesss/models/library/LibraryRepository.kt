package com.example.fitnesss.models.library

import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.workouts.Workouts

interface LibraryRepository {

    suspend fun getLibrary(): List<Library>
    suspend fun getWorkouts(libraryId: Int): List<Workouts>
    suspend fun getDetail(workoutId: Int): Detail

    suspend fun updateFavoriteStatus(workoutId: Int, isFavorite: Boolean)
    suspend fun getFavoritesWorkout(): List<Workouts>
}