package com.example.marcossanchez.ui.pantallaAnadirJuegos

import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.ui.common.UiEvent

data class AnadirJuegoState (
    val videojuego: Videojuego,
    val event: UiEvent? = null
)