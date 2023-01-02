package com.example.fitnesss.data.room.tuples

import androidx.room.ColumnInfo
import com.example.fitnesss.models.detail.Detail

data class DetailTuple(
    @ColumnInfo(name = "image_exercise")
    val imageExerciseDetail: String,
    @ColumnInfo(name = "description_exercise")
    val descriptionExerciseDetail: String,
    @ColumnInfo(name = "name_exercise")
    val nameExerciseDetail: String,
    @ColumnInfo(name = "repeat_exercise")
    val repeatExercise: String,
    @ColumnInfo(name = "time_relaxation")
    val timeRelaxation: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)

fun DetailTuple.toDetail() = Detail(
    imageExerciseDetail = imageExerciseDetail,
    descriptionExerciseDetail = descriptionExerciseDetail,
    nameExerciseDetail = nameExerciseDetail,
    repeatExercise = repeatExercise,
    timeRelaxation = timeRelaxation,
    isFavorite = isFavorite
)