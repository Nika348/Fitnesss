package com.example.fitnesss.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesss.models.library.Library
import com.example.fitnesss.models.library.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryFragmentViewModel @Inject constructor(
    private val repository : LibraryRepository
) : ViewModel() {

    private val _listLibraryFlow = MutableStateFlow<List<Library>>(emptyList())
    val listLibraryFlow = _listLibraryFlow.asStateFlow()

    init {
        getLibrary()
    }

    private fun getLibrary(){
        viewModelScope.launch(Dispatchers.IO) {
            _listLibraryFlow.tryEmit(repository.getLibrary())
        }
    }
}