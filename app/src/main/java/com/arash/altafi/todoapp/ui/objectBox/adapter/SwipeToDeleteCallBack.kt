package com.arash.altafi.todoapp.ui.objectBox.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.todoapp.ui.objectBox.ToDoObjectBoxViewModel
import com.arash.altafi.todoapp.utils.runAfter

abstract class SwipeToDeleteCallbackObjectBox(
    private val objectBoxViewModel: ToDoObjectBoxViewModel,
    private val adapter: RecyclerAdapterObjectBox
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        objectBoxViewModel.delete(adapter.getToDo(viewHolder.adapterPosition))
        runAfter(200, {
            objectBoxViewModel.getAllData()
        })
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

}