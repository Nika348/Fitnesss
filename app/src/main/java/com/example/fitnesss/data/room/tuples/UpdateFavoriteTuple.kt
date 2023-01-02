package com.example.fitnesss.data.room.tuples

import androidx.room.ColumnInfo

data class UpdateFavoriteTuple(
    @ColumnInfo(name = "workout_id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)