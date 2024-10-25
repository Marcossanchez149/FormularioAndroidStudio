package com.example.marcossanchez.domain.usecases.videojuegos

import com.example.marcossanchez.data.Repository
import com.example.marcossanchez.domain.modelo.Videojuego

class DeleteVideojuego(private val repo : Repository) {
        operator fun invoke(videojuego: Videojuego): Boolean {
            return if (repo.comprobarVideojuego(videojuego)) {
                repo.borrarVideojuego(videojuego)
                true
            } else {
                false
            }
        }
    }