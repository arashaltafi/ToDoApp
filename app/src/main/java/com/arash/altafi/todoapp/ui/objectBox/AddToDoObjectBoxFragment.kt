package com.arash.altafi.todoapp.ui.objectBox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arash.altafi.todoapp.databinding.FragmentAddToDoObjectBoxBinding
import com.arash.altafi.todoapp.utils.Constance
import com.arash.altafi.todoapp.utils.setBackStackLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddToDoObjectBoxFragment : Fragment() {

    private val binding by lazy {
        FragmentAddToDoObjectBoxBinding.inflate(layoutInflater)
    }

    private val args by navArgs<AddToDoObjectBoxFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init()
        return binding.root
    }

    private fun init() = binding.apply {
        if (args.id != 0) {
            requireActivity().title = "Edit ToDo"
            edtTitle.setText(args.title)
            edtDescription.setText(args.description)
            btnAdd.text = "Update Item"
        } else {
            requireActivity().title = "Add ToDo"
            btnAdd.text = "Add New Item"
        }

        btnAdd.setOnClickListener {
            val title = edtTitle.text.toString().trim()
            val description = edtDescription.text.toString().trim()
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill title and description",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                findNavController().apply {
                    setBackStackLiveData(
                        Constance.BACK_FROM_ADD_TODO_ObjectBox,
                        Triple(args.id, title, description)
                    )
                }
            }
        }
    }

}