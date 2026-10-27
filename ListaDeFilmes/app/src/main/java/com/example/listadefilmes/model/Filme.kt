package com.example.listadefilmes.model

data class Filme(
    val id: Int = 0,
    val nome: String,
    val genero: String,
    val sinopse: String,
    val duracao: String
){
    override fun toString(): String {
        return nome // Apenas o nome do filme será exibido na lista
    }
}
