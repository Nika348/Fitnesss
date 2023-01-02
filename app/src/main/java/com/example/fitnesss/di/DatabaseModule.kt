package com.example.fitnesss.di

import android.content.Context
import androidx.room.Room
import com.example.fitnesss.data.room.WorkoutsRoomDatabase
import com.example.fitnesss.data.room.dao.WorkoutsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WorkoutsRoomDatabase{
        return Room.databaseBuilder(
            context,
            WorkoutsRoomDatabase::class.java,
            "db"
        )
            .createFromAsset("initial_db.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(workoutsRoomDatabase: WorkoutsRoomDatabase): WorkoutsDao {
        return workoutsRoomDatabase.getWorkoutsDao()
    }
}