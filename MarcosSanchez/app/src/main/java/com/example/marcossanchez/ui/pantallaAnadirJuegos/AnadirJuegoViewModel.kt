package com.example.marcossanchez.ui.pantallaAnadirJuegos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marcossanchez.constantes.Constantes
import com.example.marcossanchez.domain.usecases.videojuegos.AnadirVideoJuego
import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.ui.common.StringProvider
import com.example.marcossanchez.ui.common.UiEvent

class AnadirJuegoViewModel (
    private val stringProvider: StringProvider,
    private val anadirVideoJuego: AnadirVideoJuego,
    ) : ViewModel() {
    private val _uiState: MutableLiveData<AnadirJuegoState> = MutableLiveData(null)
    val uiState: LiveData<AnadirJuegoState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(event = null)
    }

    fun addVideojuego(videoJuego: Videojuego):Boolean {
        if (!anadirVideoJuego(videoJuego)) {
            _uiState.value = _uiState
                .value?.copy(event = UiEvent.ShowSnackbar((Constantes.ERROR)))
            return false
        } else {
            _uiState.value = _uiState
                .value?.copy(event = UiEvent.PopBackStack)
            return true
        }
    }
}

class AnadirViewModelFactory(
    private val stringProvider: StringProvider,
    private val anadirVideoJuego: AnadirVideoJuego,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnadirJuegoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnadirJuegoViewModel(
                stringProvider,
                anadirVideoJuego,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}