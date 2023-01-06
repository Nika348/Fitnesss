package com.example.fitnesss.screens.detail

import app.cash.turbine.test
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.models.workouts.Workouts
import com.example.fitnesss.screens.workouts.WorkoutsViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class DetailViewModelTest {

    @Test
    fun `should getDetail get data`() {
        val workoutsId = 1
        val repository = Mockito.mock<LibraryRepository>()
        val viewModel = DetailViewModel(repository, workoutsId)

        runBlocking {
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
            viewModel.detailFlow.test {
                assertEquals(expected, awaitItem())
            }
            viewModel.isFavoriteFlow.test {
                assertEquals(true, expectMostRecentItem())
            }
        }
    }

    @Test
    fun `should check the value`() {
        val workoutsId = 1
        val repository = Mockito.mock<LibraryRepository>()
        val viewModel = DetailViewModel(repository, workoutsId)
        runBlocking {
            whenever(repository.updateFavoriteStatus(workoutsId, true))

            viewModel.updateFavoriteStatus()
            viewModel.isFavoriteFlow.test {
                assertEquals(true, awaitItem())
            }
        }
    }
}