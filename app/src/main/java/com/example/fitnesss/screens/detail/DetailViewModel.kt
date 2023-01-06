package com.example.fitnesss.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.library.LibraryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    private val repository: LibraryRepository,
    @Assisted("workoutsId") private val workoutsId: Int
): ViewModel() {

    private val _detailFlow = MutableStateFlow<Detail?>(null)
    val detailFlow = _detailFlow.asStateFlow()

    private val _isFavoriteFlow = MutableStateFlow(false)
    val isFavoriteFlow = _isFavoriteFlow.asStateFlow()

    init {
        getDetail()
    }

    fun getDetail(){
        viewModelScope.launch(Dispatchers.IO) {
            val detail = repository.getDetail(workoutsId)
            _isFavoriteFlow.tryEmit(detail.isFavorite)
            _detailFlow.tryEmit(detail)
        }
    }

    fun updateFavoriteStatus() {
        _isFavoriteFlow.tryEmit(!_isFavoriteFlow.value)
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteStatus(workoutsId, _isFavoriteFlow.value)
        }
    }


    @AssistedFactory
    interface Factory{
        fun create(@Assisted("workoutsId") workoutsId: Int): DetailViewModel
    }
}