package com.example.marcossanchez.domain.Usecases.videojuegos

import com.example.marcossanchez.data.Repository
import com.example.marcossanchez.domain.modelo.Videojuego

class UpdateVideojuego(private val repo : Repository) {
    operator fun invoke(id: Int, videojuego: Videojuego) {
        repo.updateVideojuego(id,videojuego)

    }
}