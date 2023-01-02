package com.example.fitnesss.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesss.models.library.LibraryRepository
import com.example.fitnesss.models.workouts.Workouts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val repository: LibraryRepository
): ViewModel() {

    private val _listFavoriteFlow = MutableStateFlow<List<Workouts>>(emptyList())
    val listFavoriteFlow = _listFavoriteFlow.asStateFlow()

    init {
        getFavorite()
    }

    private fun getFavorite(){
        viewModelScope.launch(Dispatchers.IO) {
            _listFavoriteFlow.tryEmit(repository.getFavoritesWorkout())
        }
    }

}