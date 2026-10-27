package com.example.listadefilmes.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.listadefilmes.data.db.DBHelper
import com.example.listadefilmes.model.Filme

class FilmeDAO (private val context: Context) {



    private val dbHelper = DBHelper(context)


    fun addFilme(filme: Filme): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", filme.nome)
            put("genero", filme.genero)
            put("sinopse", filme.sinopse)
            put("duracao", filme.duracao)
        }
        val id = db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllFilmes(): List<Filme> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, null,
            null, null, null, null)
        val filmeList = mutableListOf<Filme>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"))
            val sinopse = cursor.getString(cursor.getColumnIndexOrThrow("sinopse"))
            val duracao = cursor.getString(cursor.getColumnIndexOrThrow("duracao"))
            filmeList.add(Filme(id, nome, genero, sinopse, duracao))
        }
        cursor.close()
        db.close()
        return filmeList
    }

    fun getFilmeById(id: Int): Filme? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null,
            "id=?", arrayOf(id.toString()), null, null, null)

        var filme: Filme? = null
        if (cursor.moveToFirst()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"))
            val sinopse = cursor.getString(cursor.getColumnIndexOrThrow("sinopse"))
            val duracao = cursor.getString(cursor.getColumnIndexOrThrow("duracao"))
            filme = Filme(id, nome, genero, sinopse, duracao)
        }
        cursor.close()
        db.close()
        return filme
    }

    fun updateFilme(filme: Filme): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", filme.nome)
            put("genero", filme.genero)
            put("sinopse", filme.sinopse)
            put("duracao", filme.duracao)
        }
        val rowsAffected = db.update(DBHelper.TABLE_NAME, values,
            "id=?", arrayOf(filme.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteFilme(id: Int): Int {
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete(DBHelper.TABLE_NAME,
            "id=?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }
}