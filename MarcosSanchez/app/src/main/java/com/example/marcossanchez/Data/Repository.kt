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
        return videojuegos
    }

    fun añadirVideojuego(videojuego: Videojuego) =
        videojuegos.add(videojuego)


    fun borrarVideojuego(id: Int) {
        videojuegos.removeAt(id)
    }

    fun updateVideojuego(id: Int, videojuego: Videojuego) {
        videojuegos[id] = videojuego
    }

    fun comprobarVideojuego(videojuego: Videojuego): Boolean {
        return videojuegos.any { it.nombre == videojuego.nombre }
    }

}
