package com.example.fitnesss.screens.workouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.models.workouts.Workouts
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class WorkoutsViewModel @AssistedInject constructor(
    private val repository: LibraryRepository,
    @Assisted("libraryId") private val libraryId: Int
): ViewModel() {

    private val _listWorkoutsFlow = MutableStateFlow<List<Workouts>>(emptyList())
    val listWorkoutsFlow = _listWorkoutsFlow.asStateFlow()

    init {
        getWorkouts()
    }

    private fun getWorkouts(){
        viewModelScope.launch(Dispatchers.IO) {
            _listWorkoutsFlow.tryEmit(repository.getWorkouts(libraryId))
        }
    }

    @AssistedFactory
    interface Factory{
        fun create(@Assisted("libraryId") libraryId: Int): WorkoutsViewModel
    }
}