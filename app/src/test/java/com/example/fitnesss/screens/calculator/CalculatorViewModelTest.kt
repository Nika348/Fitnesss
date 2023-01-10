package com.example.fitnesss.screens.calculator

import app.cash.turbine.test
import com.example.fitnesss.models.api_model.IdealWeightModel
import com.example.fitnesss.models.api_model.IndexModel
import com.example.fitnesss.models.library.LibraryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CalculatorViewModelTest {

    @Test
    fun `should receive Index data on button click`() {
        val repository: LibraryRepository = mock()
        val viewModel = CalculatorViewModel(repository)

        runBlocking {
            val expectedIndex = IndexModel(
                20.06,
                "Normal",
                "18.5 - 25"
            )
            whenever(repository.getIndexModel(35, 175, 65)).thenReturn(expectedIndex)

            viewModel.onButtonClick(35, 175, 65, "male")
            viewModel.indexFlow.test {
                Assert.assertEquals(expectedIndex, awaitItem())
            }
        }
    }

    @Test
    fun `should receive Ideal Weight on button click`() {
        val repository: LibraryRepository = mock()
        val viewModel = CalculatorViewModel(repository)

        runBlocking {
            val expectedWeight = IdealWeightModel(
                67.09,
                70.01,
                68.05,
                73.09
            )
            whenever(repository.getIdealWeightModel("male", 175)).thenReturn(expectedWeight)

            viewModel.onButtonClick(35, 175, 65, "male")
            viewModel.idealWeightFlow.test {
                Assert.assertEquals(expectedWeight, awaitItem())
            }
        }
    }
}