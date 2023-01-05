package com.example.fitnesss.models.library

import com.example.fitnesss.data.api.ApiService
import com.example.fitnesss.data.room.dao.WorkoutsDao
import com.example.fitnesss.data.room.entity.toLibrary
import com.example.fitnesss.data.room.tuples.UpdateFavoriteTuple
import com.example.fitnesss.data.room.tuples.toDetail
import com.example.fitnesss.data.room.tuples.toWorkoutsList
import com.example.fitnesss.models.api_model.IdealWeightModel
import com.example.fitnesss.models.api_model.IndexModel
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.workouts.Workouts
import com.example.fitnesss.utils.NetworkException
import com.example.fitnesss.utils.UnknownException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibraryItem @Inject constructor(
    private val workoutsDao: WorkoutsDao,
    private val apiService: ApiService
) : LibraryRepository {

    override val favoritesWorkout: Flow<List<Workouts>>
        get() = flow {
            workoutsDao.getFavoritesWorkouts().collect {
                emit(it.toWorkoutsList())
            }
        }

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
        workoutsDao.updateFavorite(
            UpdateFavoriteTuple(
                id = workoutId,
                isFavorite = isFavorite
            )
        )
    }

    override suspend fun getIndexModel(age: Int, height: Int, weight: Int): IndexModel {
        try {
            val response = apiService.getIndex(age, weight, height)
            if (!response.isSuccessful && response.body() == null) {
                throw UnknownException()
            }
            return response.body()!!.data
        } catch (e: Exception) {
            when (e) {
                is IOException -> throw NetworkException()
                else -> throw UnknownException()
            }
        }
    }

    override suspend fun getIdealWeightModel(gender: String, height: Int): IdealWeightModel {
        try {
            val response = apiService.getIdealWeight(gender, height)
            if (!response.isSuccessful && response.body() == null) {
                throw UnknownException()
            }
            return response.body()!!.data
        } catch (e: Exception) {
            when (e) {
                is IOException -> throw NetworkException()
                else -> throw UnknownException()
            }
        }
    }
}