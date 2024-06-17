package com.example.learncook

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learncook.adaptadores.RecetaAdapter
import com.example.learncook.databinding.ActivityBuscarIngredienteBinding
import com.example.learncook.interfaces.ListenerRecycleReceta
import com.example.learncook.modelo.LearnCookDB
import com.example.learncook.poko.RecetaDatos

class BuscarIngredienteActivity : AppCompatActivity(), ListenerRecycleReceta {
    private lateinit var binding: ActivityBuscarIngredienteBinding
    private lateinit var learnCookDB: LearnCookDB
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var recetaAdapter: RecetaAdapter
    private lateinit var recetas: List<RecetaDatos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscarIngredienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        learnCookDB = LearnCookDB(this)

        // Obtener todos los nombres de ingredientes desde la base de datos
        val nombresIngredientes = learnCookDB.obtenerNombresTodosIngredientes()

        // Configurar el Spinner con los nombres de los ingredientes
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresIngredientes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIngredientes.adapter = adapter

        // Configurar el botón de agregar ingrediente
        binding.btnAgregar.setOnClickListener {
            val ingrediente = binding.spinnerIngredientes.selectedItem.toString()
            agregarIngrediente(ingrediente)
        }

        // Configurar el botón de búsqueda
        binding.btnBuscar.setOnClickListener {
            val ingredientesSeleccionados = obtenerIngredientesSeleccionados()
            if (ingredientesSeleccionados.isNotEmpty()) {
                buscarRecetasPorIngredientes(ingredientesSeleccionados)
            } else {
                Toast.makeText(this, "Agregue al menos un ingrediente", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el RecyclerView
        binding.recycleRecetas.layoutManager = LinearLayoutManager(this)
    }

    private fun agregarIngrediente(ingrediente: String) {
        // Crear una nueva fila para la tabla
        val nuevaFila = TableRow(this)
        val textView = TextView(this)
        textView.text = ingrediente
        textView.setTextColor(Color.WHITE)
        textView.setPadding(16, 16, 16, 16)
        nuevaFila.addView(textView)

        // Añadir la nueva fila a la tabla
        binding.tblIngredientes.addView(nuevaFila)

        Toast.makeText(this, "Ingrediente agregado: $ingrediente", Toast.LENGTH_SHORT).show()
    }

    private fun obtenerIngredientesSeleccionados(): List<String> {
        val ingredientesSeleccionados = mutableListOf<String>()
        for (i in 0 until binding.tblIngredientes.childCount) {
            val fila = binding.tblIngredientes.getChildAt(i) as TableRow
            val textView = fila.getChildAt(0) as TextView
            ingredientesSeleccionados.add(textView.text.toString())
        }
        return ingredientesSeleccionados
    }

    private fun buscarRecetasPorIngredientes(ingredientes: List<String>) {
        recetas = learnCookDB.buscarRecetasPorIngredientes(ingredientes)

        if (recetas.isNotEmpty()) {
            recetaAdapter = RecetaAdapter(recetas, this)
            binding.recycleRecetas.adapter = recetaAdapter
        } else {
            Toast.makeText(this, "No se encontraron recetas con los ingredientes seleccionados", Toast.LENGTH_SHORT).show()
        }
    }

    override fun clicCalificarReceta(receta: RecetaDatos, position: Int) {
        // Lógica para calificar receta
    }

    override fun clicCompartirReceta(receta: RecetaDatos, position: Int) {
        // Lógica para compartir receta
    }

    override fun clicEliminarReceta(receta: RecetaDatos, position: Int) {
        // Lógica para eliminar receta
    }

    override fun clicEditarReceta(receta: RecetaDatos, position: Int) {
        // Lógica para editar receta
    }
}
