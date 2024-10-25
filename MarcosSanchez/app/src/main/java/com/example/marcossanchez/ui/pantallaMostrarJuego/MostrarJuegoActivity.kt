package com.example.marcossanchez.ui.pantallaMostrarJuego

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marcossanchez.R
import com.example.marcossanchez.constantes.Constantes
import com.example.marcossanchez.data.Repository
import com.example.marcossanchez.databinding.ActivityUpdateDeleteBinding
import com.example.marcossanchez.domain.usecases.videojuegos.GetVideoJuegos
import com.example.marcossanchez.domain.usecases.videojuegos.DeleteVideojuego
import com.example.marcossanchez.domain.usecases.videojuegos.UpdateVideojuego
import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.ui.common.UiEvent


class MostrarJuegoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDeleteBinding

    private val viewModel: MostrarJuegoViewModel by viewModels {
        DetalleViewModelFactory(
            GetVideoJuegos(Repository),
            UpdateVideojuego(Repository),
            DeleteVideojuego(Repository)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent.extras?.let {
            val nameVideojuego = it.getString("nombre")
            viewModel.getVideojuegoPorNombre(nameVideojuego)
        }

        binding = ActivityUpdateDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mostrarJuego)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        eventos()
        observarViewModel()
    }
    private fun observarViewModel() {
        viewModel.uiState.observe(this@MostrarJuegoActivity) { state ->
            state?.let {
                state.event?.let { event ->
                    if (event is UiEvent.PopBackStack) {
                        this@MostrarJuegoActivity.finish()
                    } else if (event is UiEvent.ShowSnackbar) {
                        Toast.makeText(this@MostrarJuegoActivity, event.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    viewModel.errorMostrado()
                }


                if (state.event == null) {
                    binding.gamenameTextUD.setText(state.videojuego.nombre)
                    binding.ratingBarUD.rating = state.videojuego.calificacion
                    binding.editTextDateUD.setText(state.videojuego.fecha)
                    binding.radioGroupUD.check(
                        when (state.videojuego.genero) {
                            Constantes.ACCION-> R.id.AccionUD
                            Constantes.AVENTURA -> R.id.AventuraUD
                            else -> R.id.otroUD
                        }
                    )
                }
            }
        }
    }
    private fun eventos() {
        with(binding) {
            upadteButtonUD.setOnClickListener {
               if( viewModel.updateVideojuego(
                       Videojuego(
                    nombre = gamenameTextUD.text.toString(),
                    calificacion = ratingBarUD.rating,
                    fecha = editTextDateUD.text.toString(),
                    genero = when (radioGroupUD.checkedRadioButtonId) {
                        R.id.AccionUD -> Constantes.ACCION
                        R.id.AventuraUD -> Constantes.AVENTURA
                        else -> Constantes.OTRO
                    }
                )
                   )){
                   finish()
               }
            }
            borrarBotonUD.setOnClickListener {
                viewModel.deleteVideojuego()
            }

        }
    }

}