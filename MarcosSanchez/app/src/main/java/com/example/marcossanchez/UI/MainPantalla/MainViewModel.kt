package com.example.marcossanchez.UI.MainPantalla

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marcossanchez.Domain.Modelo.Videojuego
import com.example.marcossanchez.Domain.Usecases.Videojuegos.AñadirVideoJuego
import com.example.marcossanchez.Domain.Usecases.Videojuegos.DeleteVideojuego
import com.example.marcossanchez.Domain.Usecases.Videojuegos.GetVideoJuegos
import com.example.marcossanchez.Domain.Usecases.Videojuegos.UpdateVideojuego
import com.example.marcossanchez.R
import com.example.marcossanchez.Utils.StringProvider

class MainViewModel(
    private val stringProvider: StringProvider,
    private val añadirJuego: AñadirVideoJuego,
    private val getVideoJuegos: GetVideoJuegos,
    private val deleteVideojuego: DeleteVideojuego,
    private val updateVideojuego: UpdateVideojuego
) : ViewModel() {
    private var indiceManejador = 0
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState(videojuego = this.getVideoJuegos()[0],
            tamaño = getVideoJuegos().size ,
            indice = 0)
    }

    fun añadirVideoJuego(videojuego: Videojuego) {
        if (!añadirJuego(videojuego)) {
            _uiState.value = MainState(
                videojuego = _uiState.value.let { videojuego },
                error = stringProvider.getString(R.string.name),
            )
            _uiState.value = _uiState
                .value?.copy(error = Constantes.ERROR)
        }
    }

    fun getVideoJuegos(id: Int) {
        val juegos = getVideoJuegos()

        if (juegos.size < id || id < 0) {
            _uiState.value = _uiState.value?.copy(error = "error")

        } else
            _uiState.value = _uiState.value?.copy(videojuego = juegos[id])
    }

    fun mostrarError() {
        _uiState.value = _uiState.value?.copy(error = null)
    }

    fun borrarVideoJuego() {
        val juegos = getVideoJuegos()
        if (juegos.size < indiceManejador || indiceManejador < 0) {
            _uiState.value = _uiState.value?.copy(error = "error")
        } else {
            deleteVideojuego(indiceManejador)
            _uiState.value = _uiState.value?.copy(videojuego = juegos[0])
        }

    }
    fun upadateVideoJuego( videojuego: Videojuego) {
        val juegos = getVideoJuegos()
        if (juegos.size < indiceManejador || indiceManejador < 0) {
            _uiState.value = _uiState.value?.copy(error = "error")
        } else {
            updateVideojuego(indiceManejador, videojuego)
            _uiState.value = _uiState.value?.copy(videojuego = juegos[0])
        }
    }

    fun retroceder() {
        if (indiceManejador > 0) {
            indiceManejador -= 1
            _uiState.value = _uiState.value?.copy(
                videojuego = getVideoJuegos()[indiceManejador],
                indice = indiceManejador
            )
        }
    }

    fun avanzar() {
        if (indiceManejador < getVideoJuegos().size - 1) {
            indiceManejador += 1
            _uiState.value = _uiState.value?.copy(
                videojuego = getVideoJuegos()[indiceManejador],
                indice = indiceManejador,
            )
        }
    }
}

    class MainViewModelFactory(
        private val stringProvider: StringProvider,
        private val añadirVideoJuego: AñadirVideoJuego,
        private val getVideoJuegos: GetVideoJuegos,
        private val deleteVideojuego: DeleteVideojuego,
        private val updateVideojuego: UpdateVideojuego
        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    stringProvider,
                    añadirVideoJuego,
                    getVideoJuegos,
                    deleteVideojuego,
                    updateVideojuego,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
