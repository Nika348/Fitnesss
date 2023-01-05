package com.example.fitnesss.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.fitnesss.data.room.entity.LibraryEntity
import com.example.fitnesss.data.room.entity.WorkoutsEntity
import com.example.fitnesss.data.room.tuples.DetailTuple
import com.example.fitnesss.data.room.tuples.UpdateFavoriteTuple
import com.example.fitnesss.data.room.tuples.WorkoutsTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutsDao {

    @Query("SELECT * FROM library")
    suspend fun getLibrary(): List<LibraryEntity>

    @Query("SELECT workout_id, image_exercise, name_exercise, calories " +
            "FROM workouts WHERE library_id_key = :libraryId")
    suspend fun getWorkouts(libraryId: Int): List<WorkoutsTuple>

    @Query("SELECT" +
            " image_exercise," +
            " description_exercise," +
            " name_exercise," +
            " repeat_exercise," +
            " time_relaxation," +
            " is_favorite" +
            " FROM workouts WHERE workout_id = :workoutId")
    suspend fun getDetail(workoutId: Int): DetailTuple

    @Update(entity = WorkoutsEntity::class)
    suspend fun updateFavorite(updateFavoriteTuple: UpdateFavoriteTuple)

    @Query("SELECT workout_id, image_exercise, name_exercise, calories " +
            "FROM workouts WHERE is_favorite = 1")
    fun getFavoritesWorkouts(): Flow<List<WorkoutsTuple>>
}