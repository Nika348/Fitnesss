package com.example.fitnesss.screens.library

import app.cash.turbine.test
import com.example.fitnesss.models.library.Library
import com.example.fitnesss.models.library.LibraryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class LibraryFragmentViewModelTest {

    @Test
    fun `should getLibrary get data`() {
        val repository = mock<LibraryRepository>()
        val viewModel = LibraryFragmentViewModel(repository)

        runBlocking {
            val expected = Library(
                1,
                "https://www.snapfitness.com/assets/d852f80c6e/gym-snapfitness.jpg",
                "Накачаться"
            )
            whenever(repository.getLibrary()).thenReturn(listOf(expected))

            viewModel.getLibrary()
            viewModel.listLibraryFlow.test{
                Assert.assertEquals(listOf(expected), awaitItem())
            }
        }
    }
}