package com.example.fitnesss.models.library

import com.example.fitnesss.data.room.dao.WorkoutsDao
import com.example.fitnesss.data.room.entity.LibraryEntity
import com.example.fitnesss.data.room.tuples.DetailTuple
import com.example.fitnesss.data.room.tuples.WorkoutsTuple
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class LibraryItemTest {

    @Test
    fun `should receive Library data from database`(){
        val workoutsDao: WorkoutsDao = mock()

        runBlocking {
            val expected = LibraryEntity(
                1,
                "https://www.snapfitness.com/assets/d852f80c6e/gym-snapfitness.jpg",
                "Накачаться"
            )
            whenever(workoutsDao.getLibrary()).thenReturn(listOf(expected))

            val actual = workoutsDao.getLibrary()
            assertEquals(listOf(expected), actual)
        }
    }

    @Test
    fun `should receive Workouts data from database`(){
        val workoutsDao: WorkoutsDao = mock()

        runBlocking {
            val expected = WorkoutsTuple(
                1,
                "https://www.snapfitness.com/assets/d852f80c6e/gym-snapfitness.jpg",
                "Ленивое упражнение",
                "30 calories"
            )
            whenever(workoutsDao.getWorkouts(1)).thenReturn(listOf(expected))

            val actual = workoutsDao.getWorkouts(1)
            assertEquals(listOf(expected), actual)
        }
    }

    @Test
    fun `should receive Detail data from database`(){
        val workoutsDao: WorkoutsDao = mock()

        runBlocking {
            val expected = DetailTuple(
                "https://www.snapfitness.com/assets/d852f80c6e/gym-snapfitness.jpg",
                "Можно просто прилечь",
                "Ленивое упражнение",
                "1",
                "30:00",
                true
            )
            whenever(workoutsDao.getDetail(1)).thenReturn(expected)

            val actual = workoutsDao.getDetail(1)
            assertEquals(expected, actual)
        }
    }

}