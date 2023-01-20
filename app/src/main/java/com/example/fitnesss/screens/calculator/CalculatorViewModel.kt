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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val repository: LibraryRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val indexFlow = MutableStateFlow<IndexModel?>(null)

    val idealWeightFlow = MutableStateFlow<IdealWeightModel?>(null)

    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow = _isLoadingFlow.asStateFlow()

    private val _showContentFlow = MutableStateFlow(false)
    val showContentFlow = _showContentFlow.asStateFlow()

    private val _editAgeStateFlow = MutableStateFlow<EditAgeState>(EditAgeState.ValidState)
    val editAgeStateFlow = _editAgeStateFlow.asStateFlow()

    private val _editWeightStateFlow = MutableStateFlow<EditWeightState>(EditWeightState.ValidState)
    val editWeightStateFlow = _editWeightStateFlow.asStateFlow()

    private val _editHeightStateFlow = MutableStateFlow<EditHeightState>(EditHeightState.ValidState)
    val editHeightStateFlow = _editHeightStateFlow.asStateFlow()

    private val _editGenderStateFlow = MutableStateFlow<EditGenderState>(EditGenderState.ValidState)
    val editGenderStateFlow = _editGenderStateFlow.asStateFlow()

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
        viewModelScope.launch(ioDispatcher) {
            _isLoadingFlow.tryEmit(true)
            val indexJob =
                launch {
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
                launch {
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
            joinAll(indexJob, idealWeightJob)
            _isLoadingFlow.tryEmit(false)
            if (indexFlow.value != null && idealWeightFlow.value != null) {
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

    fun onEditAgeFocusListener(hasFocus: Boolean, age: String) {
        if (hasFocus) {
            _editAgeStateFlow.tryEmit(EditAgeState.ValidState)
        } else {
            if (age.isBlank()) {
                _editAgeStateFlow.tryEmit(EditAgeState.EmptyFieldError(R.string.input_error))
            } else if (age.toInt() !in 0..80) {
                _editAgeStateFlow.tryEmit(EditAgeState.DiapasonError(R.string.age_error))
            }
        }
    }

    fun onEditWeightFocusListener(hasFocus: Boolean, age: String) {
        if (hasFocus) {
            _editWeightStateFlow.tryEmit(EditWeightState.ValidState)
        } else {
            if (age.isBlank()) {
                _editWeightStateFlow.tryEmit(EditWeightState.EmptyFieldError(R.string.input_error))
            } else if (age.toInt() !in 40..160) {
                _editWeightStateFlow.tryEmit(EditWeightState.DiapasonError(R.string.weight_error))
            }
        }
    }

    fun onEditHeightFocusListener(hasFocus: Boolean, age: String) {
        if (hasFocus) {
            _editHeightStateFlow.tryEmit(EditHeightState.ValidState)
        } else {
            if (age.isBlank()) {
                _editHeightStateFlow.tryEmit(EditHeightState.EmptyFieldError(R.string.input_error))
            } else if (age.toInt() !in 130..230) {
                _editHeightStateFlow.tryEmit(EditHeightState.DiapasonError(R.string.height_error))
            }
        }
    }

    fun onEditGenderFocusListener(hasFocus: Boolean, age: String) {
        if (hasFocus) {
            _editGenderStateFlow.tryEmit(EditGenderState.ValidState)
        } else {
            if (age.isBlank()) {
                _editGenderStateFlow.tryEmit(EditGenderState.EmptyFieldError(R.string.input_error))
            }
        }
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
    // имеет реализации только в том же пакете и модуле
    sealed interface Commands {
        class ShowBottomNetworkError(@StringRes val message: Int) : Commands

        class ShowBottomUnknown(@StringRes val message: Int) : Commands
    }

    sealed interface EditAgeState {
        class EmptyFieldError(@StringRes val message: Int) : EditAgeState
        class DiapasonError(@StringRes val message: Int) : EditAgeState
        object ValidState : EditAgeState
    }

    sealed interface EditWeightState {
        class EmptyFieldError(@StringRes val message: Int) : EditWeightState
        class DiapasonError(@StringRes val message: Int) : EditWeightState
        object ValidState : EditWeightState
    }

    sealed interface EditHeightState {
        class EmptyFieldError(@StringRes val message: Int) : EditHeightState
        class DiapasonError(@StringRes val message: Int) : EditHeightState
        object ValidState : EditHeightState
    }

    sealed interface EditGenderState {
        class EmptyFieldError(@StringRes val message: Int) : EditGenderState
        object ValidState : EditGenderState
    }
}