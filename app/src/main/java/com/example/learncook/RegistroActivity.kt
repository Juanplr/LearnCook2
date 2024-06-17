package com.example.learncook

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learncook.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {
    private lateinit var bingind: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bingind = ActivityRegistroBinding.inflate(layoutInflater)
        val view = bingind.root
        setContentView(view)



    }
}