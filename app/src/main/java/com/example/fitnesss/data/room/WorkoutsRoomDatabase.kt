package com.example.fitnesss.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnesss.data.room.dao.WorkoutsDao
import com.example.fitnesss.data.room.entity.LibraryEntity
import com.example.fitnesss.data.room.entity.WorkoutsEntity
import com.example.fitnesss.models.workouts.Workouts

@Database(entities = [LibraryEntity::class, WorkoutsEntity::class], version = 2, exportSchema = true)
abstract class WorkoutsRoomDatabase: RoomDatabase() {

    abstract fun getWorkoutsDao(): WorkoutsDao
}