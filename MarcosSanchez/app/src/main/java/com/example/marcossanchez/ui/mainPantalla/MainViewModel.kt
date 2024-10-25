package com.example.marcossanchez.ui.mainPantalla

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marcossanchez.domain.Usecases.videojuegos.GetVideoJuegos

class MainViewModel(
    val getVideoJuegos: GetVideoJuegos,

    ) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        getVideoJuegosFun()
    }

     fun getVideoJuegosFun() {
        _uiState.value = _uiState.value?.copy(videojuegos = getVideoJuegos.invoke())
    }


    fun mostrarError() {
        _uiState.value = _uiState.value?.copy(error = null)
    }



}

    class MainViewModelFactory(
        private val getVideoJuegos: GetVideoJuegos,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    getVideoJuegos,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
