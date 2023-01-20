package com.example.fitnesss.screens.workouts

import app.cash.turbine.test
import com.example.fitnesss.models.library.Library
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.models.workouts.Workouts
import com.example.fitnesss.screens.library.LibraryFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WorkoutsViewModelTest {

    @Test
    fun `should getWorkouts get data`() = runTest {
        val libraryId = 1
        val repository = Mockito.mock<LibraryRepository>()
        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = WorkoutsViewModel(repository,dispatcher, libraryId)

        val expected = Workouts(
            1,
            "https://www.snapfitness.com/assets/d852f80c6e/gym-snapfitness.jpg",
            "Просто упражнение",
            "1000 калорий"
        )
        whenever(repository.getWorkouts(libraryId)).thenReturn(listOf(expected))

        viewModel.getWorkouts()
        advanceUntilIdle()
        viewModel.listWorkoutsFlow.test {
            assertEquals(listOf(expected), awaitItem())
        }
    }
}