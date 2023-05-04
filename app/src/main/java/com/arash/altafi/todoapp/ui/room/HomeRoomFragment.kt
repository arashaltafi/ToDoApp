package com.arash.altafi.todoapp.ui.room

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.arash.altafi.todoapp.databinding.FragmentHomeRoomBinding
import com.arash.altafi.todoapp.domain.room.models.ToDoRoom
import com.arash.altafi.todoapp.ui.room.adapter.RecyclerAdapterRoom
import com.arash.altafi.todoapp.ui.room.adapter.SwipeToDeleteCallBackRoom
import com.arash.altafi.todoapp.utils.Constance
import com.arash.altafi.todoapp.utils.getBackStackLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeRoomFragment : Fragment() {

    private val toDoViewModel: ToDoRoomViewModel by viewModels()

    private val binding by lazy {
        FragmentHomeRoomBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var adapter: RecyclerAdapterRoom

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

        /* // To Get All Data
        CoroutineScope(Dispatchers.Main).launch {
            toDoViewModel.getAllData()
        }*/

        lifecycleScope.launch {
            toDoViewModel.liveToDo.collect {
                adapter.setDataList(it)
            }
        }

        fabAdd.setOnClickListener {
            findNavController().navigate(
                HomeRoomFragmentDirections.actionHomeRoomFragmentToAddToDoRoomFragment()
            )
        }

        swipe.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

        val swipeHandler =
            object : SwipeToDeleteCallBackRoom(toDoViewModel, adapter) {}
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rcTodo)

        adapter.onItemClick = { toDo ->
            findNavController().navigate(
                HomeRoomFragmentDirections.actionHomeRoomFragmentToAddToDoRoomFragment(
                    id = toDo.id,
                    title = toDo.title,
                    description = toDo.description
                )
            )
        }

        adapter.onItemStateChange = { toDo, isChecked ->
            toDo.done = isChecked
            MainScope().launch { toDoViewModel.update(toDo) }
        }

    }

    private fun initBackStackObservers() {
        findNavController().getBackStackLiveData<Triple<Int, String, String>>(Constance.BACK_FROM_ADD_TODO_ROOM)
            ?.observe(this) {
                val id = it.first
                val title = it.second
                val description = it.third
                if (id != 0) {
                    val toDo = ToDoRoom(
                        id = id,
                        title = title,
                        description = description,
                        done = false
                    )
                    CoroutineScope(Dispatchers.Main).launch { toDoViewModel.update(toDo) }
                    Toast.makeText(requireContext(), "Todo Updated", Toast.LENGTH_SHORT).show()
                } else {
                    val toDo = ToDoRoom(
                        title = title,
                        description = description,
                        done = false
                    )
                    CoroutineScope(Dispatchers.Main).launch { toDoViewModel.insert(toDo) }
                    Toast.makeText(requireContext(), "New Todo Added", Toast.LENGTH_SHORT).show()
                }
                requireActivity().intent = Intent() // clear intent
            }
    }

}