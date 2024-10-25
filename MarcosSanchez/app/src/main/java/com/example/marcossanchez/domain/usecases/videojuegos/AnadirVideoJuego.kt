package com.example.marcossanchez.domain.usecases.videojuegos



import com.example.marcossanchez.data.Repository
import com.example.marcossanchez.domain.modelo.Videojuego

class AnadirVideoJuego(private val repository : Repository) {
    operator fun invoke(videojuego: Videojuego): Boolean {
        return repository.anadirVideojuego(videojuego)
    }
}