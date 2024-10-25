package com.example.marcossanchez.ui.pantallaAnadirJuegos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marcossanchez.R
import com.example.marcossanchez.constantes.Constantes
import com.example.marcossanchez.data.Repository
import com.example.marcossanchez.databinding.ActivityAnadirBinding
import com.example.marcossanchez.domain.usecases.videojuegos.AnadirVideoJuego
import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.ui.common.StringProvider
import com.example.marcossanchez.ui.common.UiEvent

class AnadirJuegoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirBinding

    private val viewModel: AnadirJuegoViewModel by viewModels {
        AnadirViewModelFactory(
            StringProvider.instance(this),
            AnadirVideoJuego(Repository),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        intent.extras?.let {
        }
        binding = ActivityAnadirBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.añadirJuego)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        eventos()
        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@AnadirJuegoActivity) { state ->
            state?.let {
                state.event?.let { event ->
                    if (event is UiEvent.PopBackStack) {
                        this@AnadirJuegoActivity.finish()
                    } else if (event is UiEvent.ShowSnackbar) {
                        Toast.makeText(this@AnadirJuegoActivity, event.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    viewModel.errorMostrado()
                }
            }
        }
    }

    private fun eventos() {
        with(binding) {
            addEnviarBotonAA.setOnClickListener {
               if( viewModel.addVideojuego(
                    Videojuego(
                        nombre = addGamenameTextAA.text.toString(),
                        genero = when (addRadioGroupAA.checkedRadioButtonId) {
                            R.id.add_AccionAA -> Constantes.ACCION
                            R.id.add_AventuraAA -> Constantes.AVENTURA
                            else -> Constantes.OTRO
                        },
                        fecha = editTextDateAA.text.toString(),
                        calificacion = addRatingBarAA.rating
                    )
                )){
                     finish()
               }
            }
        }
    }
}