package com.example.learncook

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learncook.databinding.ActivityAgregarRecetaBinding
import com.example.learncook.modelo.LearnCookDB
import com.example.learncook.poko.Ingrediente
import com.example.learncook.poko.Receta

class AgregarRecetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarRecetaBinding
    private lateinit var modelo: LearnCookDB
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listaDeIngredientes: MutableList<Ingrediente>
    private val ingredientesSeleccionados = mutableListOf<Ingrediente>()
    private var idUsuario = 0
    private var presupuesto =0.0
    private var listaId = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modelo = LearnCookDB(this)
        idUsuario = intent.getIntExtra("idUsuario", -1)

        listaDeIngredientes = modelo.traerIngredientes().toMutableList()

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaDeIngredientes.map { it.nombre }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spIngredientesReceta.adapter = adapter

        binding.spIngredientesReceta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position >= 0 && position < listaDeIngredientes.size) {
                    val ingredienteSeleccionado = listaDeIngredientes[position]

                    ingredientesSeleccionados.add(ingredienteSeleccionado)

                    val textoActual = binding.etIngredientes.text.toString()
                    binding.etIngredientes.setText(textoActual + ingredienteSeleccionado.nombre + "\n")
                    presupuesto += ingredienteSeleccionado.precio
                    listaId.add(ingredienteSeleccionado.id)

                    listaDeIngredientes.removeAt(position)

                    adapter.clear()
                    adapter.addAll(listaDeIngredientes.map { it.nombre })
                    adapter.notifyDataSetChanged()

                    binding.spIngredientesReceta.setSelection(-1)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.btnAgregarReceta.setOnClickListener {
            if (validarDatos()) {

                var receta = Receta(0,idUsuario,binding.etNombreReceta.text.toString(),binding.etTiempoReceta.text.toString(),presupuesto,binding.etPreparacion.text.toString())
                var agregado = modelo.agregarReceta(receta)
                if (agregado>0){
                    var ultimoId = modelo.traerUltimoIdDeReceta()
                    var agregado = modelo.agregarIngredientes(ultimoId,listaId)
                    if (agregado==1){
                        Toast.makeText(this@AgregarRecetaActivity, "Receta creada", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this@AgregarRecetaActivity, "Error al agregar los ingredientes", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validarDatos(): Boolean {
        var bandera = true
        if(binding.etNombreReceta.text.isEmpty()||binding.etTiempoReceta.text.isEmpty()||binding.etPreparacion.text.isEmpty()||binding.etIngredientes.text.isEmpty()){
            bandera = false
            Toast.makeText(this@AgregarRecetaActivity, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
        }
        return bandera
    }
}