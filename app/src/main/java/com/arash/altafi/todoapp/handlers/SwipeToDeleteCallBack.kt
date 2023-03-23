package com.arash.altafi.todoapp.handlers

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.todoapp.adapters.RecyclerAdapter
import com.arash.altafi.todoapp.viewModels.ToDoViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

abstract class SwipeToDeleteCallback(
    private val context: Context,
    private val viewModel: ToDoViewModel,
    private val adapter: RecyclerAdapter
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        MainScope().launch {
            viewModel.delete(adapter.getToDo(viewHolder.adapterPosition))
            Toast.makeText(context, "ToDo Item Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

}