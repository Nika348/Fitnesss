package com.example.fitnesss.screens.workouts

import androidx.lifecycle.ViewModel
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.models.workouts.Workouts
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class WorkoutsViewModel @AssistedInject constructor(
    private val repository: LibraryRepository,
    @Assisted("libraryId") private val libraryId: Int
): ViewModel() {

    private val _data = MutableStateFlow<List<Workouts>>(emptyList())
    val data = _data.asStateFlow()

    init {
        _data.tryEmit(repository.getWorkouts(libraryId))
    }

    @AssistedFactory
    interface Factory{
        fun create(@Assisted("libraryId") libraryId: Int): WorkoutsViewModel
    }
}