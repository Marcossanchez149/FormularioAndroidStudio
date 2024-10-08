package com.example.marcossanchez.Domain.Usecases.Videojuegos

import com.example.marcossanchez.Data.Repository

class DeleteVideojuego(private val repo : Repository) {
    operator fun invoke(id: Int) {
         repo.borrarVideojuego(id)
    }
}