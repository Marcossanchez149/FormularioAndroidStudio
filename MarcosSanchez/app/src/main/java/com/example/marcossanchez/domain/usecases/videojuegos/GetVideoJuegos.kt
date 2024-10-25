package com.example.marcossanchez.domain.usecases.videojuegos

import com.example.marcossanchez.data.Repository

class GetVideoJuegos(private val repo : Repository) {

     operator fun invoke()= repo.getVideojuegos()
}