package com.example.marcossanchez.UI.MainPantalla

import com.example.marcossanchez.Domain.Modelo.Videojuego

data class MainState(
    val videojuego: Videojuego = Videojuego(),
    val error: String? = null,
    val tamaño: Int = 0,
    val indice : Int = 0,
)
