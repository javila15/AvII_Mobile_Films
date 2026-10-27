package com.example.listadefilmes.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.listadefilmes.R
import com.example.listadefilmes.data.dao.FilmeDAO
import com.example.listadefilmes.model.Filme

class MainActivity : AppCompatActivity() {

    private lateinit var dao: FilmeDAO
    private lateinit var listView: ListView
    private var filmes: List<Filme> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = FilmeDAO(this)
        listView = findViewById(R.id.lvFilmes)

     
        findViewById<Button>(R.id.btnNovoFilme).setOnClickListener {
            startActivityForResult(Intent(this, FormularioFilmeActivity::class.java), 1)
        }

   
        listView.setOnItemClickListener { _, _, position, _ ->
            val filme = filmes[position]
            val intent = Intent(this, FormularioFilmeActivity::class.java)
            intent.putExtra(FormularioFilmeActivity.EXTRA_ID, filme.id)
            startActivityForResult(intent, 1)
        }

        carregarLista()
    }

    private fun carregarLista() {
        filmes = dao.getAllFilmes()
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filmes)
    }

   
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            carregarLista()
        }
    }
}
