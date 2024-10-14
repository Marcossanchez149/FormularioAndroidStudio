package com.example.marcossanchez.domain.Usecases.videojuegos

import com.example.marcossanchez.data.Repository

class DeleteVideojuego(private val repo : Repository) {
    operator fun invoke(id: Int) {
         repo.borrarVideojuego(id)
    }
}