package com.example.listadefilmes.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listadefilmes.R
import com.example.listadefilmes.model.Filme

class FilmeAdapter : RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {
    private val filmes = mutableListOf<Filme>()

    fun atualizarLista(novaLista: List<Filme>) {
        filmes.clear()
        filmes.addAll(novaLista)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filme, parent, false)
        return FilmeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        holder.bind(filmes[position])
    }

    override fun getItemCount(): Int = filmes.size

    class FilmeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nome: TextView = itemView.findViewById(R.id.textNomeFilme)
        private val genero: TextView = itemView.findViewById(R.id.textGeneroFilme)
        private val duracao: TextView = itemView.findViewById(R.id.textDuracaoFilme)

        fun bind(filme: Filme) {
            nome.text = filme.nome
            genero.text = filme.genero
            duracao.text = filme.duracao
        }
    }
}
