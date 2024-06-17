package com.example.learncook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learncook.databinding.ActivityBuscarPresupuestoBinding
import com.example.learncook.fragmentos.RecetaFragment
import com.example.learncook.modelo.LearnCookDB
import com.example.learncook.poko.Receta

class BuscarPresupuestoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuscarPresupuestoBinding
    private lateinit var db: LearnCookDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscarPresupuestoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = LearnCookDB(this)

        binding.Buscar.setOnClickListener {
            val presupuestoStr = binding.edtPresupuesto.text.toString()
            if (presupuestoStr.isNotEmpty()) {
                val presupuesto = presupuestoStr.toDouble()
               // val recetas = buscarRecetasPorPresupuesto(presupuesto)
                //mostrarRecetas(recetas)
            } else {
                Toast.makeText(this, "Ingrese un presupuesto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //ivate fun buscarRecetasPorPresupuesto(presupuesto: Double): List<Receta> {
        //return db.buscarRecetasPorPresupuesto(presupuesto)
    //}

    //private fun mostrarRecetas(recetas: List<Receta>) {
      //  val fragment = RecetaFragment.newInstance(recetas)
       // val transaction = supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.Buscar_presupuesto, fragment)
        //transaction.addToBackStack(null)
      //  transaction.commit()
    //}

}