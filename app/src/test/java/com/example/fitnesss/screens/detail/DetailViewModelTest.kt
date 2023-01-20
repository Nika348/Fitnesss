package com.example.fitnesss.screens.detail

import app.cash.turbine.test
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.models.workouts.Workouts
import com.example.fitnesss.screens.workouts.WorkoutsViewModel
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
class DetailViewModelTest {

    @Test
    fun `should getDetail get data`() = runTest {
        val workoutsId = 1
        val repository = Mockito.mock<LibraryRepository>()
        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = DetailViewModel(repository, dispatcher, workoutsId)

        val expected = Detail(
            "https://www.snapfitness.com/assets/d852f80c6e/gym-snapfitness.jpg",
            "Можно просто прилечь",
            "Ленивое упражнение",
            "1",
            "30:00",
            true
        )
        whenever(repository.getDetail(workoutsId)).thenReturn(expected)

        viewModel.getDetail()
        advanceUntilIdle()
        viewModel.detailFlow.test {
            assertEquals(expected, awaitItem())
        }
        viewModel.isFavoriteFlow.test {
            assertEquals(true, expectMostRecentItem())
        }
    }

    @Test
    fun `should check the value`() = runTest {
        val workoutsId = 1
        val repository = Mockito.mock<LibraryRepository>()
        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = DetailViewModel(repository, dispatcher, workoutsId)
        whenever(repository.updateFavoriteStatus(workoutsId, true))

        viewModel.updateFavoriteStatus()
        advanceUntilIdle()
        viewModel.isFavoriteFlow.test {
            assertEquals(true, awaitItem())
        }
    }
}