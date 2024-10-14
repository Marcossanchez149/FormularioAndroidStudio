package com.example.marcossanchez.domain.Usecases.videojuegos

import com.example.marcossanchez.data.Repository
import com.example.marcossanchez.domain.modelo.Videojuego

class ComprobarVideojuego(private val repository : Repository) {
    operator fun invoke(videojuego: Videojuego): Boolean {
        return repository.comprobarVideojuego(videojuego)
    }
}