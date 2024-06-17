package com.example.learncook.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learncook.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "idUsuario"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idUsuario = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.home.text = ""+idUsuario
    }

    companion object {
        @JvmStatic
        fun newInstance(idUsuario: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, idUsuario)
                }
            }
    }
}
