package com.example.fitnesss.screens.detail

import androidx.lifecycle.ViewModel
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.library.LibraryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel @AssistedInject constructor(
    private val repository: LibraryRepository,
    @Assisted("workoutsId") private val workoutsId: Int
): ViewModel() {

    private val _data = MutableStateFlow<Detail?>(null)
    val data = _data.asStateFlow()

    init {
        _data.tryEmit(repository.getDetail(workoutsId))
    }

    @AssistedFactory
    interface Factory{
        fun create(@Assisted("workoutsId") workoutsId: Int): DetailViewModel
    }
}