package com.example.fitnesss.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workouts",
    //Ускоряет поиск по указанной колонке, но снижает скорость записи в бд
    indices = [Index("library_id_key")],
    //Внешний ключ: колонка одной таблицы ссылается на колонку другой
    foreignKeys = [ForeignKey(
        entity = LibraryEntity::class,
        parentColumns = ["library_id"],
        childColumns = ["library_id_key"]
    )]
)
data class WorkoutsEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_id")
    val id: Int,
    @ColumnInfo(name = "image_exercise")
    val imageExercise: String,
    @ColumnInfo(name = "name_exercise")
    val nameExercise: String,
    @ColumnInfo(name = "calories")
    val calories: String,
    @ColumnInfo(name = "description_exercise")
    val descriptionExercise: String,
    @ColumnInfo(name = "repeat_exercise")
    val repeatExercise: String,
    @ColumnInfo(name = "time_relaxation")
    val timeRelaxation: String,
    @ColumnInfo(name = "library_id_key")
    val libraryId: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)