package com.example.learncook.fragmentos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learncook.AgregarRecetaActivity
import com.example.learncook.adaptadores.RecetaAdapter
import com.example.learncook.databinding.FragmentRecetaBinding
import com.example.learncook.interfaces.ListenerRecycleReceta
import com.example.learncook.modelo.LearnCookDB
import com.example.learncook.poko.RecetaDatos

private const val ARG_ID_USUARIO = "idUsuario"

class RecetaFragment : Fragment(), ListenerRecycleReceta {
    private lateinit var binding: FragmentRecetaBinding
    private var idUsuario: Int = -1
    private lateinit var modelo: LearnCookDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idUsuario = it.getInt(ARG_ID_USUARIO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecetaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modelo = LearnCookDB(requireContext())

        binding.btnAgregarReceta.setOnClickListener {
            val intent = Intent(requireContext(), AgregarRecetaActivity::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }
        cargarMisRecetas()
        configuracionRecycle()

    }

    companion object {
        @JvmStatic
        fun newInstance(idUsuario: Int) =
            RecetaFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID_USUARIO, idUsuario)
                }
            }
    }

    override fun clicEditarReceta(receta: RecetaDatos, position: Int) {
        // Implementación pendiente según lo necesario
    }

    override fun clicEliminarReceta(receta: RecetaDatos, position: Int) {
        // Implementación pendiente según lo necesario
    }

    override fun clicCalificarReceta(receta: RecetaDatos, position: Int) {
        // Implementación pendiente según lo necesario
    }

    override fun clicCompartirReceta(receta: RecetaDatos, position: Int) {
        // Implementación pendiente según lo necesario
    }
    private fun cargarMisRecetas(){
        val recetas = modelo.obtenerRecetasDatosPorUsuario(idUsuario);
        if(recetas.size>0){
            binding.tvMensajeRecetas.visibility = View.GONE
            binding.recycleRecetas.visibility = View.VISIBLE
            binding.recycleRecetas.adapter = RecetaAdapter(recetas,this@RecetaFragment)
        }else{
            binding.tvMensajeRecetas.visibility = View.VISIBLE
            binding.recycleRecetas.visibility = View.GONE
        }
    }
    private fun configuracionRecycle(){
        binding.recycleRecetas.layoutManager = LinearLayoutManager(context)
        binding.recycleRecetas.setHasFixedSize(true)
    }
}
