package com.example.listadefilmes.controller

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listadefilmes.R
import com.example.listadefilmes.data.dao.FilmeDAO
import com.example.listadefilmes.model.Filme

class MainActivity : AppCompatActivity() {
    private lateinit var dao: FilmeDAO
    private lateinit var adapter: FilmeAdapter
    private lateinit var campoBusca: EditText
    private lateinit var spinnerGenero: Spinner
    private lateinit var mensagemVazia: TextView
    private var todosFilmes: List<Filme> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = FilmeDAO(this)
        campoBusca = findViewById(R.id.editBusca)
        spinnerGenero = findViewById(R.id.spinnerGenero)
        mensagemVazia = findViewById(R.id.textListaVazia)
        adapter = FilmeAdapter()

        findViewById<RecyclerView>(R.id.recyclerFilmes).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            setHasFixedSize(true)
        }

        campoBusca.addTextChangedListener { atualizarLista() }
        spinnerGenero.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                atualizarLista()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    override fun onResume() {
        super.onResume()
        carregarFilmes()
    }

    private fun carregarFilmes() {
        todosFilmes = dao.getAllFilmes()
        val generoAtual = spinnerGenero.selectedItem?.toString()
        val generos = listOf(FilmeFiltro.TODOS) + todosFilmes
            .map { it.genero.trim() }
            .filter { it.isNotEmpty() }
            .distinctBy { it.lowercase() }
            .sortedBy { it.lowercase() }
        spinnerGenero.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            generos
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        val indice = generos.indexOfFirst { it.equals(generoAtual, ignoreCase = true) }
        spinnerGenero.setSelection(if (indice >= 0) indice else 0)
        atualizarLista()
    }

    private fun atualizarLista() {
        if (!::adapter.isInitialized) return
        val genero = spinnerGenero.selectedItem?.toString() ?: FilmeFiltro.TODOS
        val resultado = FilmeFiltro.aplicar(
            todosFilmes,
            campoBusca.text?.toString().orEmpty(),
            genero
        )
        adapter.atualizarLista(resultado)
        mensagemVazia.visibility = if (resultado.isEmpty()) View.VISIBLE else View.GONE
        mensagemVazia.text = if (todosFilmes.isEmpty()) {
            getString(R.string.lista_sem_filmes)
        } else {
            getString(R.string.lista_sem_resultados)
        }
    }
}
