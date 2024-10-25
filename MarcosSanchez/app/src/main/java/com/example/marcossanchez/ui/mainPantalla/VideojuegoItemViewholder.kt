package com.example.marcossanchez.ui.mainPantalla

import VideojuegoAdapter
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.marcossanchez.databinding.ActivityMostrarListaBinding
import com.example.marcossanchez.domain.modelo.Videojuego

class VideojuegoItemViewholder(itemView: View, val actions:VideojuegoAdapter.VideojuegoActions): RecyclerView.ViewHolder(itemView) {
    private val binding = ActivityMostrarListaBinding.bind(itemView)

    fun bind(item: Videojuego){
        with(binding) {
            nombreLista.text = item.nombre
            itemView.setOnLongClickListener{
                true
            }
            itemView.setOnClickListener {
                actions.onItemClick(item)
            }
        }
    }
}