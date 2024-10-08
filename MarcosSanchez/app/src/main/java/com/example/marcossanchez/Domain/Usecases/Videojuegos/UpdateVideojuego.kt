package com.example.marcossanchez.Domain.Usecases.Videojuegos

import com.example.marcossanchez.Data.Repository
import com.example.marcossanchez.Domain.Modelo.Videojuego

class UpdateVideojuego(private val repo : Repository) {
    operator fun invoke(id: Int, videojuego: Videojuego) {
        repo.updateVideojuego(id,videojuego)

    }
}