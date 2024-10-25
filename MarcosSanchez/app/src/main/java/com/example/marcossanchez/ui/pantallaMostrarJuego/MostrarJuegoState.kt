package com.example.marcossanchez.ui.pantallaMostrarJuego

import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.ui.common.UiEvent

data class MostrarJuegoState (
    val videojuego: Videojuego,
    val event: UiEvent? = null
)