package com.example.marcossanchez.Domain.Usecases.Videojuegos



import com.example.marcossanchez.Data.Repository
import com.example.marcossanchez.Domain.Modelo.Videojuego

class AñadirVideoJuego(private val repository : Repository) {
    operator fun invoke(videojuego: Videojuego): Boolean {
        return repository.añadirVideojuego(videojuego)
    }
}