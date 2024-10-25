package com.example.marcossanchez.ui.mainPantalla

import com.example.marcossanchez.domain.modelo.Videojuego

data class MainState(
    val videojuegos: List<Videojuego> =  emptyList(),
    val error: String? = null,
)
