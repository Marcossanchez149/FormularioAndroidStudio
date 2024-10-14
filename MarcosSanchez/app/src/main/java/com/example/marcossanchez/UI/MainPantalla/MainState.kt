package com.example.marcossanchez.UI.mainPantalla

import com.example.marcossanchez.domain.modelo.Videojuego

data class MainState(
    val videojuego: Videojuego = Videojuego(),
    val error: String? = null,
    val tamaño: Int = 0,
    val indice : Int = 0,
    val sePuedeRetroceder: Boolean = true,
    val sePuedeAvanzar: Boolean = true,

)
