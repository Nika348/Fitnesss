package com.example.fitnesss.screens.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesss.models.library.Library
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.models.workouts.Workouts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LibraryFragmentViewModel @Inject constructor(
    private val repository : LibraryRepository
) : ViewModel() {


    private val _data = MutableStateFlow<List<Library>>(emptyList())
    val data = _data.asStateFlow()

    init {
        _data.value = repository.getLibrary()
    }
}