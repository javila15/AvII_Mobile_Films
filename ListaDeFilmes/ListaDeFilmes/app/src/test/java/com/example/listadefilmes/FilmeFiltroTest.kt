package com.example.listadefilmes

import com.example.listadefilmes.controller.FilmeFiltro
import com.example.listadefilmes.model.Filme
import org.junit.Assert.assertEquals
import org.junit.Test

class FilmeFiltroTest {
    private val filmes = listOf(
        Filme(1, "Toy Story", "Animação", "", "1h 21min"),
        Filme(2, "A Origem", "Ficção científica", "", "2h 28min"),
        Filme(3, "Amélie", "Drama", "", "2h 02min")
    )

    @Test
    fun ordenaPorNomeIgnorandoMaiusculasEAcentos() {
        val resultado = FilmeFiltro.aplicar(filmes, "", FilmeFiltro.TODOS)
        assertEquals(listOf("A Origem", "Amélie", "Toy Story"), resultado.map { it.nome })
    }

    @Test
    fun pesquisaPorNomeIgnoraAcentosEMaiusculas() {
        val resultado = FilmeFiltro.aplicar(filmes, "amelie", FilmeFiltro.TODOS)
        assertEquals(listOf("Amélie"), resultado.map { it.nome })
    }

    @Test
    fun combinaBuscaComFiltroDeGenero() {
        val resultado = FilmeFiltro.aplicar(filmes, "a", "Drama")
        assertEquals(listOf("Amélie"), resultado.map { it.nome })
    }
}
