package com.arash.altafi.todoapp.ui.room.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.todoapp.ui.room.ToDoRoomViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

abstract class SwipeToDeleteCallBackRoom(
    private val viewModel: ToDoRoomViewModel,
    private val adapter: RecyclerAdapterRoom
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        MainScope().launch {
            viewModel.delete(adapter.getToDo(viewHolder.adapterPosition))
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