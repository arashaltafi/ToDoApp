package com.arash.altafi.todoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arash.altafi.todoapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init()
        return binding.root
    }

    private fun init() = binding.apply {
        btnSampleObjectBox.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToHomeObjectBoxFragment()
            )
        }

        btnSampleRoom.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToHomeRoomFragment()
            )
        }
    }

}