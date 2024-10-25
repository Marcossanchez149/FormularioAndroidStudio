package com.example.marcossanchez.data

import com.example.marcossanchez.domain.modelo.Videojuego

object Repository {
    private val videojuegos = mutableListOf<Videojuego>()

    init {
        videojuegos.add(Videojuego( "Mario",  "Aventura", "1985", 3.0f))
        videojuegos.add(Videojuego( "Zelda",  "Aventura", "1986", 2.0f))
        videojuegos.add(Videojuego("Batman","Accion","1989",4.0f))
    }
    fun getVideojuegos(): List<Videojuego> {
        return videojuegos.toList()
    }

    fun anadirVideojuego(videojuego: Videojuego): Boolean {
      return videojuegos.add(videojuego)
    }


    fun borrarVideojuego(videojuego: Videojuego) {
        videojuegos.remove(videojuego)
    }

    fun updateVideojuego( videojuego: Videojuego) {
        val index = videojuegos.indexOfFirst { it.nombre == videojuego.nombre }
        videojuegos[index] = videojuego
    }

    fun comprobarVideojuego(videojuego: Videojuego): Boolean {
        return videojuegos.any { it.nombre == videojuego.nombre }
    }

}
