import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.marcossanchez.R
import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.ui.mainPantalla.MainActivity
import com.example.marcossanchez.ui.mainPantalla.VideojuegoItemViewholder

class VideojuegoAdapter(
    private val itemClick: (Videojuego) -> Unit,
    private val actions: VideojuegoActions
) : ListAdapter<Videojuego, VideojuegoItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideojuegoItemViewholder {
        return VideojuegoItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_mostrar_lista, parent, false),
            actions,
        )
    }
    override fun onBindViewHolder(holder: VideojuegoItemViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<Videojuego>() {
        override fun areItemsTheSame(oldItem: Videojuego, newItem: Videojuego): Boolean {
            return oldItem.nombre == newItem.nombre
        }

        override fun areContentsTheSame(oldItem: Videojuego, newItem: Videojuego): Boolean {
            return oldItem == newItem
        }
    }
    interface VideojuegoActions {
        fun onItemClick(videojuego: Videojuego)
    }
}