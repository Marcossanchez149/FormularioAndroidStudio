package com.example.marcossanchez.ui.mainPantalla

import com.example.marcossanchez.ui.pantallaMostrarJuego.MostrarJuegoActivity
import VideojuegoAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marcossanchez.R
import com.example.marcossanchez.data.Repository
import com.example.marcossanchez.databinding.ActivityMainBinding
import com.example.marcossanchez.domain.modelo.Videojuego
import com.example.marcossanchez.domain.usecases.videojuegos.GetVideoJuegos
import com.example.marcossanchez.ui.common.MarginItemDecoration
import com.example.marcossanchez.ui.pantallaAnadirJuegos.AnadirJuegoActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: VideojuegoAdapter

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            GetVideoJuegos(Repository),
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
        observarState()
        configurarRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVideoJuegosFun()
    }

    private fun observarState() {
        viewModel.uiState.observe(this@MainActivity) { state ->
            adapter.submitList(state.videojuegos)
        }
    }

    private fun configurarRecyclerView() {
        adapter = VideojuegoAdapter(itemClick = { videojuego ->
            navigateToDetail(videojuego.nombre)
        },
            actions = object : VideojuegoAdapter.VideojuegoActions {
                override fun onItemClick(videojuego: Videojuego) {
                    navigateToDetail(videojuego.nombre)
                }
            })
        binding.listaVideojuegos.layoutManager = LinearLayoutManager(this)
        binding.listaVideojuegos.adapter = adapter
        binding.listaVideojuegos.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin))
        )
    }

    private fun navigateToDetail(name: String) {
        val intent = Intent(this, MostrarJuegoActivity::class.java)
        intent.putExtra("nombre", name)
        startActivity(intent)
    }

    private fun navigateToAnadir() {
        val intent = Intent(this, AnadirJuegoActivity::class.java)
        startActivity(intent)
    }

    private fun eventos() {
        binding.botonIr.setOnClickListener {
            navigateToAnadir()
        }
    }
}