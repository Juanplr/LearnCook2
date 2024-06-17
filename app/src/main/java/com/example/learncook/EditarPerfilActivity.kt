package com.example.learncook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learncook.databinding.ActivityEditarPerfilBinding
import com.example.learncook.fragmentos.PerfilFragment
import com.example.learncook.modelo.LearnCookDB
import com.example.learncook.poko.Usuario

class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarPerfilBinding
    private lateinit var db: LearnCookDB
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = LearnCookDB(this)

        val idUsuario = intent.getIntExtra("idUsuario", -1)
        Log.d("EditarPerfilActivity", "idUsuario recibido: $idUsuario")
        usuario = db.traerUsuario2(idUsuario) ?: run {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Set the user data in the EditText fields
        binding.etCorreoActual.setText(usuario.correo)
        binding.etNuevoCorreo.setText(usuario.correo)
        binding.etContraseAActual.setText(usuario.contrasena)

        // Set the listeners for the buttons
        binding.btnGuardarCambios.setOnClickListener {
            val correoNuevo = binding.etNuevoCorreo.text.toString()
            val contrasenaNueva = binding.etNuevaContraseA.text.toString()

            if (correoNuevo.isEmpty() || contrasenaNueva.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correoNuevo).matches()) {
                Toast.makeText(this, "Por favor, ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (db.isCorreo(correoNuevo) && correoNuevo != usuario.correo) {
                Toast.makeText(this, "El correo electrónico ya está en uso", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.actualizarContrasena(usuario.correo, contrasenaNueva)
            db.actualizarCorreo(usuario.correo, correoNuevo)

            Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PerfilFragment::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)

            finish()
        }
    }
}
