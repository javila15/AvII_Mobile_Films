package com.example.listadefilmes.controller

import com.example.listadefilmes.model.Filme
import java.text.Normalizer
import java.util.Locale

/** Regras de pesquisa, filtro de gênero e ordenação da listagem. */
object FilmeFiltro {
    fun aplicar(filmes: List<Filme>, consulta: String, generoSelecionado: String): List<Filme> {
        val busca = normalizar(consulta.trim())
        return filmes
            .asSequence()
            .filter { busca.isBlank() || normalizar(it.nome).contains(busca) }
            .filter { generoSelecionado == TODOS || it.genero.equals(generoSelecionado, ignoreCase = true) }
            .sortedBy { normalizar(it.nome) }
            .toList()
    }

    private fun normalizar(texto: String): String = Normalizer
        .normalize(texto.lowercase(Locale.ROOT), Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "")

    const val TODOS = "Todos os gêneros"
}
