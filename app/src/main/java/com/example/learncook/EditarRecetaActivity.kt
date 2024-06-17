package com.example.learncook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learncook.databinding.ActivityEditarRecetaBinding
import com.example.learncook.modelo.LearnCookDB
import com.example.learncook.poko.Receta

class EditarRecetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarRecetaBinding
    private lateinit var modelo: LearnCookDB
    private var idReceta = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        modelo = LearnCookDB(this@EditarRecetaActivity)
        idReceta = intent.getIntExtra("idReceta", -1)
        val receta = modelo.obtenerReceta(idReceta)
        if (receta != null) {
            llenarDatos(receta)
        } else {
            Toast.makeText(this, "Error al obtener la receta", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnEditarReceta.setOnClickListener {
            if (validarDatos()) {
                var receta2 = receta?.let { it1 -> Receta(it1.id,receta.idUsuario,binding.etNombreReceta.text.toString(),binding.etTiempoReceta.text.toString(),receta.presupuseto,binding.etPreparacion.text.toString()) }
                val numero = modelo.modificarReceta(receta2!!)
                if (numero > 0) {
                    Toast.makeText(this, "Receta editada", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al editar receta", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun llenarDatos(receta: Receta) {
        binding.etNombreReceta.setText(receta.nombreReceta)
        binding.etTiempoReceta.setText(receta.tiempo)
        binding.etPreparacion.setText(receta.preparacion)
    }

    private fun validarDatos(): Boolean {
        return binding.etNombreReceta.text.isNotEmpty() &&
                binding.etTiempoReceta.text.isNotEmpty() &&
                binding.etPreparacion.text.isNotEmpty()
    }
}
