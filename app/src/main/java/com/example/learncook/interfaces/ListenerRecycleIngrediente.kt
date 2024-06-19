package com.example.learncook.interfaces

import com.example.learncook.poko.Ingrediente

interface ListenerRecycleIngrediente {
    fun clicEliminarIngrediente(ingrediente: Ingrediente, position: Int)
}