package com.example.marcossanchez.UI.MainPantalla

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marcossanchez.Data.Repository
import com.example.marcossanchez.Domain.Modelo.Videojuego
import com.example.marcossanchez.Domain.Usecases.Videojuegos.AñadirVideoJuego
import com.example.marcossanchez.Domain.Usecases.Videojuegos.DeleteVideojuego
import com.example.marcossanchez.Domain.Usecases.Videojuegos.GetVideoJuegos
import com.example.marcossanchez.Domain.Usecases.Videojuegos.UpdateVideojuego
import com.example.marcossanchez.R
import com.example.marcossanchez.Utils.StringProvider
import com.example.marcossanchez.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AñadirVideoJuego(Repository),
            GetVideoJuegos(Repository),
            DeleteVideojuego(Repository),
            UpdateVideojuego(Repository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        eventos()
        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@MainActivity) { state ->
            state.error?.let { error ->
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                viewModel.mostrarError()
            }

            state.videojuego?.let { videojuego ->
                binding.gamenameText.setText(videojuego.nombre)
                binding.editTextDate.setText(videojuego.fecha)
                binding.ratingBar.rating = videojuego.calificacion
                when (videojuego.genero) {
                    "Acción" -> binding.radioGroup.check(R.id.Accion)
                    "Aventuras" -> binding.radioGroup.check(R.id.Aventura)
                    else -> binding.radioGroup.clearCheck()
                }
            }
        }
    }

    private fun eventos() {
        with(binding) {
            enviarBoton.setOnClickListener {
                if(checkBox.isChecked){
                    val selectedGenero = when (radioGroup.checkedRadioButtonId) {
                        Accion.id -> "Aventuras"
                        Aventura.id -> "Acción"
                        else -> "Otro"
                    }
                    viewModel.añadirVideoJuego( Videojuego(
                        gamenameText.text.toString(),
                        selectedGenero,
                        editTextDate.text.toString(),
                        ratingBar.rating
                    ))
                    Snackbar.make(findViewById(android.R.id.content), "Usuario añadido", Snackbar.LENGTH_SHORT).show()
                }
            }
            izquierdaBoton.setOnClickListener {
                viewModel.retroceder()
            }
            derechaBoton.setOnClickListener {
                viewModel.avanzar()
            }

            borrarBoton.setOnClickListener {
                viewModel.borrarVideoJuego()
            }
            upadteButton.setOnClickListener{
                val selectedGenero = when (radioGroup.checkedRadioButtonId) {
                    Accion.id -> "Aventuras"
                    Aventura.id -> "Acción"
                    else -> "Otro"
                }
                viewModel.upadateVideoJuego( Videojuego(
                    gamenameText.text.toString(),
                    selectedGenero,
                    editTextDate.text.toString(),
                    ratingBar.rating
                ))
            }

            upadteButton.setOnClickListener{
                val selectedGenero = when (radioGroup.checkedRadioButtonId) {
                    Accion.id -> "Aventuras"
                    Aventura.id -> "Acción"
                    else -> "Otro"
                }
                viewModel.upadateVideoJuego( Videojuego(
                    gamenameText.text.toString(),
                    selectedGenero,
                    editTextDate.text.toString(),
                    ratingBar.rating
                ))
            }
        }
    }
}