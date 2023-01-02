package com.example.fitnesss.data.room.tuples

import androidx.room.ColumnInfo
import com.example.fitnesss.models.workouts.Workouts

data class WorkoutsTuple(
    @ColumnInfo(name = "workout_id")
    val id: Int,
    @ColumnInfo(name = "image_exercise")
    val imageExercise: String,
    @ColumnInfo(name = "name_exercise")
    val nameExercise: String,
    @ColumnInfo(name = "calories")
    val calories: String
)

fun WorkoutsTuple.toWorkouts() = Workouts(
    id = id,
    imageExercise = imageExercise,
    nameExercise = nameExercise,
    calories = calories
)

fun List<WorkoutsTuple>.toWorkoutsList(): List<Workouts>{
    return this.map { it.toWorkouts() }
}