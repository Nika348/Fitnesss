package com.example.fitnesss.screens.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesss.models.library.Library
import com.example.fitnesss.models.library.LibraryItem
import com.example.fitnesss.models.library.LibraryRepository

class LibraryFragmentViewModel: ViewModel() {

    private val repository : LibraryRepository = LibraryItem()
    private val _data = MutableLiveData<List<Library>>()
    val data: LiveData<List<Library>> get() = _data

    init {
        _data.value = repository.getLibrary()
    }
}