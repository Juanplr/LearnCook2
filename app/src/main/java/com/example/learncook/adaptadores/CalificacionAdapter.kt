package com.example.learncook.adaptadores

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learncook.interfaces.ListenerRecycleCalificacion
import com.example.learncook.poko.Calificacion

class CalificacionAdapter (val calificaciones: List<Calificacion>, val listener : ListenerRecycleCalificacion): RecyclerView.Adapter<CalificacionAdapter.ViewHolderCalificacion>(){

    class ViewHolderCalificacion(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCalificacion {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderCalificacion, position: Int) {
        TODO("Not yet implemented")
    }
}