package com.example.fitnesss.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnesss.models.library.Library

@Entity(tableName = "library")
data class LibraryEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "library_id")
    val id: Int,
    @ColumnInfo(name = "image_library")
    val imageLibrary: String,
    @ColumnInfo(name = "text_library")
    val textLibrary: String
)

fun LibraryEntity.toLibrary() = Library(
    id = id,
    imageLibrary = imageLibrary,
    textLibrary = textLibrary
)