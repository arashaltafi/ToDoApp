package com.arash.altafi.todoapp.ui.objectBox

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.arash.altafi.todoapp.databinding.FragmentHomeObjectBoxBinding
import com.arash.altafi.todoapp.domain.objectBox.models.ToDoObjectBox
import com.arash.altafi.todoapp.ui.objectBox.adapter.RecyclerAdapterObjectBox
import com.arash.altafi.todoapp.ui.objectBox.adapter.SwipeToDeleteCallbackObjectBox
import com.arash.altafi.todoapp.utils.Constance
import com.arash.altafi.todoapp.utils.getBackStackLiveData
import com.arash.altafi.todoapp.utils.runAfter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeObjectBoxFragment : Fragment() {

    private val toDoViewModel: ToDoObjectBoxViewModel by viewModels()

    private val binding by lazy {
        FragmentHomeObjectBoxBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var adapter: RecyclerAdapterObjectBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackStackObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init() = binding.apply {
        rcTodo.adapter = adapter

        toDoViewModel.liveToDo.observe(viewLifecycleOwner) {
            adapter.setDataList(it)
        }

        fabAdd.setOnClickListener {
            findNavController().navigate(
                HomeObjectBoxFragmentDirections.actionHomeObjectBoxFragmentToAddToDoObjectBoxFragment()
            )
        }

        swipe.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

        val swipeHandler =
            object : SwipeToDeleteCallbackObjectBox(toDoViewModel, adapter) {}
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rcTodo)

        adapter.onItemClick = { toDo ->
            findNavController().navigate(
                HomeObjectBoxFragmentDirections.actionHomeObjectBoxFragmentToAddToDoObjectBoxFragment(
                    id = toDo.id.toInt(),
                    title = toDo.title,
                    description = toDo.description
                )
            )
        }

        adapter.onItemStateChange = { toDo, isChecked ->
            toDo.done = isChecked
            toDoViewModel.update(toDo)
        }

    }

    private fun initBackStackObservers() {
        findNavController().getBackStackLiveData<Triple<Int, String, String>>(Constance.BACK_FROM_ADD_TODO_ObjectBox)
            ?.observe(this) {
                val id = it.first
                val title = it.second
                val description = it.third
                if (id != 0) {
                    val toDo = ToDoObjectBox(
                        id = id.toLong(),
                        title = title,
                        description = description,
                        done = false
                    )
                    toDoViewModel.update(toDo)
                    Toast.makeText(requireContext(), "Todo Updated", Toast.LENGTH_SHORT).show()
                } else {
                    val toDo = ToDoObjectBox(
                        title = title,
                        description = description,
                        done = false
                    )
                    toDoViewModel.insert(toDo)
                    Toast.makeText(requireContext(), "New Todo Added", Toast.LENGTH_SHORT).show()
                }

                runAfter(200, {
                    toDoViewModel.getAllData()
                })

                requireActivity().intent = Intent() // clear intent
            }
    }

}