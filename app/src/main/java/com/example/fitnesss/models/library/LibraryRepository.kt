package com.example.fitnesss.models.library

import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.workouts.Workouts

interface LibraryRepository {

    fun getLibrary(): List<Library>
    fun getWorkouts(libraryId: Int): List<Workouts>
    fun getDetail(workoutsId: Int): Detail
}