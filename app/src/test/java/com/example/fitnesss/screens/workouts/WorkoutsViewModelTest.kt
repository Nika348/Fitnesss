package com.example.fitnesss.screens.workouts

import app.cash.turbine.test
import com.example.fitnesss.models.library.Library
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.models.workouts.Workouts
import com.example.fitnesss.screens.library.LibraryFragmentViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class WorkoutsViewModelTest {

    @Test
    fun `should getWorkouts get data`() {
        val libraryId = 1
        val repository = Mockito.mock<LibraryRepository>()
        val viewModel = WorkoutsViewModel(repository, libraryId)

        runBlocking {
            val expected = Workouts(
                1,
                "https://www.snapfitness.com/assets/d852f80c6e/gym-snapfitness.jpg",
                "Просто упражнение",
                "1000 калорий"
            )
            whenever(repository.getWorkouts(libraryId)).thenReturn(listOf(expected))

            viewModel.getWorkouts()
            viewModel.listWorkoutsFlow.test{
                assertEquals(listOf(expected), awaitItem())
            }
        }
    }
}