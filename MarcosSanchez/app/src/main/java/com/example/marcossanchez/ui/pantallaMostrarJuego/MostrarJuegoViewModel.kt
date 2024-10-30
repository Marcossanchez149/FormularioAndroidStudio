package com.example.marcossanchez.ui.pantallaMostrarJuego

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marcossanchez.constantes.Constantes
import com.example.marcossanchez.domain.usecases.videojuegos.DeleteVideojuego
import com.example.marcossanchez.domain.usecases.videojuegos.GetVideoJuegos
import com.example.marcossanchez.domain.usecases.videojuegos.UpdateVideojuego
import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.ui.common.UiEvent

class MostrarJuegoViewModel(
    private val getVideojuegos: GetVideoJuegos,
    private val deleteVideojuegoUseCase: DeleteVideojuego,
    private val updateVideojuegoUseCase: UpdateVideojuego,
) : ViewModel() {
    private val _uiState: MutableLiveData<MostrarJuegoState> = MutableLiveData(null)
    val uiState: LiveData<MostrarJuegoState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(event = null)
    }

    fun deleteVideojuego() {
        _uiState.value?.let {
            if (!deleteVideojuegoUseCase(it.videojuego)) {
                _uiState.value = _uiState
                    .value?.copy(event = UiEvent.ShowSnackbar(Constantes.ERROR))
            } else {
                _uiState.value = _uiState
                    .value?.copy(event = UiEvent.PopBackStack)
            }
        }

    }
    fun getVideojuegoPorNombre(nombre: String?) {
        val videojuegos = getVideojuegos()
        val videojuego = videojuegos.stream().filter{ it.nombre == nombre }.findFirst().orElse(null)
        if (videojuego == null) {
            _uiState.value?.copy(event = UiEvent.ShowSnackbar(Constantes.ERROR))
        } else
            _uiState.value = _uiState.value?.copy(videojuego = videojuego) ?: MostrarJuegoState(videojuego)
    }
    fun updateVideojuego(videojuego: Videojuego): Boolean {
        if (!updateVideojuegoUseCase(videojuego)) {
            _uiState.value = _uiState
                .value?.copy(event = UiEvent.ShowSnackbar(Constantes.ERROR))
            return false
        }else
            return true
    }
}

class DetalleViewModelFactory(
    private val getVideojuegos: GetVideoJuegos,
    private val updateVideojuego: UpdateVideojuego,
    private val deleteVideojuego: DeleteVideojuego,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MostrarJuegoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MostrarJuegoViewModel(
                getVideojuegos,
                deleteVideojuego,
                updateVideojuego,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}