package com.example.marcossanchez.Domain.Usecases.Videojuegos

import com.example.marcossanchez.Data.Repository

class GetVideoJuegos(private val repo : Repository) {

     operator fun invoke()= repo.getVideojuegos()
}