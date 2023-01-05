package com.example.fitnesss.screens.calculator

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesss.R
import com.example.fitnesss.models.api_model.IdealWeightModel
import com.example.fitnesss.models.api_model.IndexModel
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.utils.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val repository: LibraryRepository
) : ViewModel() {

    private val indexFlow = MutableStateFlow<IndexModel?>(null)

    private val idealWeightFlow = MutableStateFlow<IdealWeightModel?>(null)

    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow = _isLoadingFlow.asStateFlow()

    private val _showContentFlow = MutableStateFlow(false)
    val showContentFlow = _showContentFlow.asStateFlow()

    private val _commandFlow = MutableSharedFlow<Commands>(
        replay = 0,
        extraBufferCapacity = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val commandFlow = _commandFlow.asSharedFlow()

    val indexAndWeightFlow: Flow<IndexAndWeightModel?>
        get() = combine(
            indexFlow,
            idealWeightFlow,
            ::merge
        )

    fun onButtonClick(age: Int, height: Int, weight: Int, gender: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingFlow.tryEmit(true)
            val indexJob =
                async {
                    try {
                        indexFlow.tryEmit(repository.getIndexModel(age, height, weight))
                    } catch (e: Exception) {
                        when (e) {
                            is NetworkException -> {
                                _commandFlow.tryEmit(Commands.ShowBottomNetworkError(R.string.network_error))
                            }
                            else -> {
                                _commandFlow.tryEmit(Commands.ShowBottomUnknown(R.string.unknown_error))
                            }
                        }
                    }
                }
            val idealWeightJob =
                async {
                    try {
                        idealWeightFlow.tryEmit(
                            repository.getIdealWeightModel(
                                Genders.parse(gender).engGender,
                                height
                            )
                        )
                    } catch (e: Exception) {
                        when (e) {
                            is NetworkException -> {
                                _commandFlow.tryEmit(Commands.ShowBottomNetworkError(R.string.network_error))
                            }
                            else -> {
                                _commandFlow.tryEmit(Commands.ShowBottomUnknown(R.string.unknown_error))
                            }
                        }
                    }
                }
            indexJob.await()
            idealWeightJob.await()
            _isLoadingFlow.tryEmit(false)
            if(indexFlow.value != null && idealWeightFlow.value != null) {
                _showContentFlow.tryEmit(true)
            }
        }
    }

    private fun merge(
        indexModel: IndexModel?,
        idealWeightModel: IdealWeightModel?
    ): IndexAndWeightModel? {
        return if (indexModel != null && idealWeightModel != null) {
            IndexAndWeightModel(
                bmi = indexModel.bmi,
                health = indexModel.health,
                healthyBmiRange = indexModel.healthyBmiRange,
                robinson = idealWeightModel.robinson
            )
        } else null
    }

    data class IndexAndWeightModel(
        val bmi: Double,
        val health: String,
        val healthyBmiRange: String,
        val robinson: Double
    )

    enum class Genders(
        val ruGender: String,
        val engGender: String
    ) {
        Male("Мужской", "male"),
        Female("Женский", "female");

        companion object {
            fun parse(value: String) = values().find { it.ruGender == value } ?: Male
        }
    }

    sealed interface Commands {
        class ShowBottomNetworkError(@StringRes val message: Int) : Commands

        class ShowBottomUnknown(@StringRes val message: Int) : Commands
    }

}