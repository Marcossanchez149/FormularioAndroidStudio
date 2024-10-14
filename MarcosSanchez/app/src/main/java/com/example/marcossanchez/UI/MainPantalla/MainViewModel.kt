package com.example.marcossanchez.UI.mainPantalla

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.domain.Usecases.videojuegos.AñadirVideoJuego
import com.example.marcossanchez.domain.Usecases.videojuegos.ComprobarVideojuego
import com.example.marcossanchez.domain.Usecases.videojuegos.DeleteVideojuego
import com.example.marcossanchez.domain.Usecases.videojuegos.GetVideoJuegos
import com.example.marcossanchez.domain.Usecases.videojuegos.UpdateVideojuego
import com.example.marcossanchez.R
import com.example.marcossanchez.utils.StringProvider

class MainViewModel(
    private val stringProvider: StringProvider,
    private val añadirJuego: AñadirVideoJuego,
    private val getVideoJuegos: GetVideoJuegos,
    private val deleteVideojuego: DeleteVideojuego,
    private val updateVideojuego: UpdateVideojuego,
    private val comprobarVideojuego: ComprobarVideojuego
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
        if(!comprobarVideojuego(videojuego)){
            if (!añadirJuego(videojuego)) {
                _uiState.value = MainState(
                    videojuego = _uiState.value.let { videojuego },
                    error = stringProvider.getString(R.string.name),
                )
                _uiState.value = _uiState
                    .value?.copy(error = Constantes.ERROR)
            }
        }else{
            _uiState.value = _uiState.value?.copy(error = "Videojuego ya existe")
        }
    }

    fun mostrarError() {
        _uiState.value = _uiState.value?.copy(error = null)
    }

    fun borrarVideoJuego() {
        if(getVideoJuegos().size == 1){
            _uiState.value = _uiState.value?.copy(error = "Solo queda este videojuego")

        }else{
            val juegos = getVideoJuegos()
            deleteVideojuego(indiceManejador)
            if (  indiceManejador != 0) {
                indiceManejador -= 1;
            }
            _uiState.value = _uiState.value?.copy(videojuego = juegos[indiceManejador])
        }


    }
    fun upadateVideoJuego( videojuego: Videojuego) {
        val juegos = getVideoJuegos()
        if (juegos.size < indiceManejador || indiceManejador < 0) {
            _uiState.value = _uiState.value?.copy(error = "error")
        } else {
            updateVideojuego(indiceManejador, videojuego)
            _uiState.value = _uiState.value?.copy(videojuego = juegos[indiceManejador])
        }
    }

    fun retroceder() {
        if (indiceManejador > 0) {
            indiceManejador -= 1
            _uiState.value = _uiState.value?.copy(
                videojuego = getVideoJuegos()[indiceManejador],
                indice = indiceManejador
            )
        }else{
           _uiState.value = _uiState.value?.copy(error = "No se puede restroceder")
        }
    }

    fun avanzar() {
        if (indiceManejador < getVideoJuegos().size - 1) {
            indiceManejador += 1
            _uiState.value = _uiState.value?.copy(
                videojuego = getVideoJuegos()[indiceManejador],
                indice = indiceManejador,
            )
        }else{
            _uiState.value = _uiState.value?.copy(error = "No se puede avanzar")
        }
    }
}

    class MainViewModelFactory(
        private val stringProvider: StringProvider,
        private val añadirVideoJuego: AñadirVideoJuego,
        private val getVideoJuegos: GetVideoJuegos,
        private val deleteVideojuego: DeleteVideojuego,
        private val updateVideojuego: UpdateVideojuego,
        private val comprobarVideojuego: ComprobarVideojuego
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
                    comprobarVideojuego
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
