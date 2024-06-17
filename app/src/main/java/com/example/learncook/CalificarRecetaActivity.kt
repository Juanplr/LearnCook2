package com.example.learncook

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.learncook.databinding.ActivityCalificarRecetaBinding

class CalificarRecetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalificarRecetaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalificarRecetaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}