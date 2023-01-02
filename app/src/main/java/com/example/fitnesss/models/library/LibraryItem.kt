package com.example.fitnesss.models.library

import com.example.fitnesss.data.room.dao.WorkoutsDao
import com.example.fitnesss.data.room.entity.toLibrary
import com.example.fitnesss.data.room.tuples.UpdateFavoriteTuple
import com.example.fitnesss.data.room.tuples.toDetail
import com.example.fitnesss.data.room.tuples.toWorkoutsList
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.workouts.Workouts
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibraryItem @Inject constructor(
    private val workoutsDao: WorkoutsDao
): LibraryRepository {
    override suspend fun getLibrary(): List<Library> {
        return workoutsDao.getLibrary().map { it.toLibrary() }
    }

    override suspend fun getWorkouts(libraryId: Int): List<Workouts> {
        return workoutsDao.getWorkouts(libraryId).toWorkoutsList()
    }

    override suspend fun getDetail(workoutId: Int): Detail {
        return workoutsDao.getDetail(workoutId).toDetail()
    }

    override suspend fun updateFavoriteStatus(workoutId: Int, isFavorite: Boolean) {
        workoutsDao.updateFavorite(UpdateFavoriteTuple(
            id = workoutId,
            isFavorite = isFavorite
        ))
    }

    override suspend fun getFavoritesWorkout(): List<Workouts> {
        return workoutsDao.getFavoritesWorkouts().toWorkoutsList()
    }
}