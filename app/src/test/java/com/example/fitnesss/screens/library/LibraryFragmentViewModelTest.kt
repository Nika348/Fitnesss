package com.example.fitnesss.screens.library

import app.cash.turbine.test
import com.example.fitnesss.models.library.Library
import com.example.fitnesss.models.library.LibraryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryFragmentViewModelTest {

    @Test
    fun `should getLibrary get data`() = runTest {
        val repository = mock<LibraryRepository>()
        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = LibraryFragmentViewModel(repository, dispatcher)

        val expected = Library(
            1,
            "https://www.snapfitness.com/assets/d852f80c6e/gym-snapfitness.jpg",
            "Накачаться"
        )
        whenever(repository.getLibrary()).thenReturn(listOf(expected))

        viewModel.getLibrary()
        advanceUntilIdle()
        viewModel.listLibraryFlow.test {
            Assert.assertEquals(listOf(expected), awaitItem())
        }
    }
}