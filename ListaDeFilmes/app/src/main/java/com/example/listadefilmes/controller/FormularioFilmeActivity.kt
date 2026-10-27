package com.example.listadefilmes.controller

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listadefilmes.R
import com.example.listadefilmes.data.dao.FilmeDAO
import com.example.listadefilmes.model.Filme

class FormularioFilmeActivity : AppCompatActivity() {

    companion object {
        // Chave usada pela lista para mandar o id do filme a ser editado
        const val EXTRA_ID = "filme_id"
    }

    private lateinit var etNome: EditText
    private lateinit var etGenero: EditText
    private lateinit var etSinopse: EditText
    private lateinit var etDuracao: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnExcluir: Button

    private lateinit var dao: FilmeDAO
    private var filmeId = 0   // 0 = novo filme, > 0 = edição

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario_filme)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dao = FilmeDAO(this)

        etNome = findViewById(R.id.etNome)
        etGenero = findViewById(R.id.etGenero)
        etSinopse = findViewById(R.id.etSinopse)
        etDuracao = findViewById(R.id.etDuracao)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnExcluir = findViewById(R.id.btnExcluir)

        // Se veio um id pelo Intent, é edição: busca o filme e preenche os campos
        filmeId = intent.getIntExtra(EXTRA_ID, 0)
        if (filmeId > 0) {
            val filme = dao.getFilmeById(filmeId)
            if (filme == null) {
                Toast.makeText(this, "Filme não encontrado", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            title = "Editar filme"
            etNome.setText(filme.nome)
            etGenero.setText(filme.genero)
            etSinopse.setText(filme.sinopse)
            etDuracao.setText(filme.duracao)
            btnExcluir.visibility = View.VISIBLE
        } else {
            title = "Novo filme"
        }

        btnSalvar.setOnClickListener { salvar() }
        btnExcluir.setOnClickListener { confirmarExclusao() }
    }

    // Todos os campos são obrigatórios
    private fun validarCampos(): Boolean {
        var valido = true

        if (etNome.text.toString().trim().isEmpty()) {
            etNome.error = "Informe o nome do filme"
            valido = false
        }
        if (etGenero.text.toString().trim().isEmpty()) {
            etGenero.error = "Informe o gênero"
            valido = false
        }
        if (etSinopse.text.toString().trim().isEmpty()) {
            etSinopse.error = "Informe a sinopse"
            valido = false
        }
        val duracao = etDuracao.text.toString().trim()
        if (duracao.isEmpty()) {
            etDuracao.error = "Informe a duração"
            valido = false
        } else if ((duracao.toIntOrNull() ?: 0) <= 0) {
            etDuracao.error = "A duração deve ser maior que zero"
            valido = false
        }

        return valido
    }

    // Inclui (id = 0) ou atualiza (id > 0), conforme o modo da tela
    private fun salvar() {
        if (!validarCampos()) return

        val filme = Filme(
            id = filmeId,
            nome = etNome.text.toString().trim(),
            genero = etGenero.text.toString().trim(),
            sinopse = etSinopse.text.toString().trim(),
            duracao = etDuracao.text.toString().trim()
        )

        if (filmeId == 0) {
            if (dao.addFilme(filme) == -1L) {
                Toast.makeText(this, "Erro ao cadastrar o filme", Toast.LENGTH_SHORT).show()
                return
            }
            Toast.makeText(this, "Filme cadastrado!", Toast.LENGTH_SHORT).show()
        } else {
            if (dao.updateFilme(filme) == 0) {
                Toast.makeText(this, "Erro ao atualizar o filme", Toast.LENGTH_SHORT).show()
                return
            }
            Toast.makeText(this, "Filme atualizado!", Toast.LENGTH_SHORT).show()
        }

        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun confirmarExclusao() {
        AlertDialog.Builder(this)
            .setTitle("Excluir filme")
            .setMessage("Tem certeza que deseja excluir este filme?")
            .setPositiveButton("Excluir") { _, _ ->
                if (dao.deleteFilme(filmeId) > 0) {
                    Toast.makeText(this, "Filme excluído!", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao excluir o filme", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
