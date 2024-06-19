package com.example.learncook

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learncook.adaptadores.IngredienteAdapter
import com.example.learncook.databinding.ActivityEditarRecetaBinding
import com.example.learncook.interfaces.ListenerRecycleIngrediente
import com.example.learncook.modelo.LearnCookDB
import com.example.learncook.poko.Ingrediente
import com.example.learncook.poko.Receta

class EditarRecetaActivity : AppCompatActivity(), ListenerRecycleIngrediente {
    private lateinit var binding: ActivityEditarRecetaBinding
    private lateinit var modelo: LearnCookDB
    private lateinit var ingredientes: List<Ingrediente>
    private var idReceta = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        modelo = LearnCookDB(this@EditarRecetaActivity)
        idReceta = intent.getIntExtra("idReceta", -1)
        val receta = modelo.obtenerReceta(idReceta)
        configuracionRecycle()
        if (receta != null) {
            llenarDatos(receta)
            ingredientes =  modelo.optenerLosIngredientesPorIdRecetas(idReceta)
            traerIngredientes()
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
        var bandera = false
        if(binding.etNombreReceta.text.isNotEmpty() && binding.etTiempoReceta.text.isNotEmpty() && binding.etPreparacion.text.isNotEmpty()){
            bandera = true
        }else{
            Toast.makeText(this, "favor de llenar todos los datos", Toast.LENGTH_SHORT).show()
        }
        return bandera
    }

    override fun clicEliminarIngrediente(ingrediente: Ingrediente, position: Int) {
        if (modelo.eliminarIngrediente(ingrediente.id)>0){
            ingredientes =  modelo.optenerLosIngredientesPorIdRecetas(idReceta)
            traerIngredientes()
            Toast.makeText(this, "Ingrediente eliminado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "No se pudo eliminar el Ingrediente", Toast.LENGTH_SHORT).show()
        }

    }

    override fun clicEditarIngrediente(ingrediente: Ingrediente, position: Int, etCantidad: EditText){
        var cantidad= etCantidad.text.toString().toDouble()
        if(etCantidad.text.isNotEmpty() && cantidad > 0){
            ingrediente.cantidad = etCantidad.text.toString().toDouble()
            if(modelo.editarIngrediente(ingrediente)>0){
                ingredientes =  modelo.optenerLosIngredientesPorIdRecetas(idReceta)
                traerIngredientes()
                Toast.makeText(this, "Ingrediente editado", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "no puedes agregar un ingrediente sin un dato o menor a 0", Toast.LENGTH_SHORT).show()
        }
    }

    private fun traerIngredientes() {
        if (ingredientes != null) {
            if (ingredientes.isNotEmpty()) {
                binding.recycleIngredientes.visibility = View.VISIBLE
                binding.recycleIngredientes.adapter = IngredienteAdapter(ingredientes, this)
            } else {
                Toast.makeText(this@EditarRecetaActivity, "No hay ingredientes", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun configuracionRecycle(){
        binding.recycleIngredientes.layoutManager = LinearLayoutManager(this@EditarRecetaActivity)
        binding.recycleIngredientes.setHasFixedSize(true)
    }

}
