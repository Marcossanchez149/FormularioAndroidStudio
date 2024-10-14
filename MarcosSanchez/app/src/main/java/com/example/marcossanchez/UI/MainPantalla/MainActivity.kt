package com.example.marcossanchez.UI.mainPantalla


import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marcossanchez.R
import com.example.marcossanchez.data.Repository
import com.example.marcossanchez.databinding.ActivityMainBinding
import com.example.marcossanchez.domain.Usecases.videojuegos.AñadirVideoJuego
import com.example.marcossanchez.domain.Usecases.videojuegos.ComprobarVideojuego
import com.example.marcossanchez.domain.Usecases.videojuegos.DeleteVideojuego
import com.example.marcossanchez.domain.Usecases.videojuegos.GetVideoJuegos
import com.example.marcossanchez.domain.Usecases.videojuegos.UpdateVideojuego
import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.utils.StringProvider



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AñadirVideoJuego(Repository),
            GetVideoJuegos(Repository),
            DeleteVideojuego(Repository),
            UpdateVideojuego(Repository),
            ComprobarVideojuego(Repository)
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

            state.videojuego.let { videojuego ->
                binding.gamenameText.setText(videojuego.nombre)
                binding.editTextDate.setText(videojuego.fecha)
                binding.ratingBar.rating = videojuego.calificacion
                when (videojuego.genero) {
                    "Accion" -> binding.radioGroup.check(R.id.Accion)
                    "Aventura" -> binding.radioGroup.check(R.id.Aventura)
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
                        Accion.id -> "Accion"
                        Aventura.id -> "Aventura"
                        else -> "Otro"
                    }
                    viewModel.añadirVideoJuego( Videojuego(
                        gamenameTextLayout.editText?.text.toString(),
                        selectedGenero,
                        editTextDate.text.toString(),
                        ratingBar.rating
                    )
                    )
                    checkBox.isChecked = false
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
                    Accion.id -> "Accion"
                    Aventura.id -> "Aventura"
                    else -> "Otro"
                }
                viewModel.upadateVideoJuego( Videojuego(
                    gamenameTextLayout.editText?.text.toString(),
                    selectedGenero,
                    binding.editTextDate.text.toString(),
                    ratingBar.rating
                ))
            }

        }
    }
}