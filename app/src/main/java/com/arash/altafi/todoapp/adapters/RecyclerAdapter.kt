package com.arash.altafi.todoapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.todoapp.R
import com.arash.altafi.todoapp.models.ToDo

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ToDoHolder>() {

    private val dataList = ArrayList<ToDo>()
    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var onItemDoneStateListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ToDoHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        val item = dataList[position]

        holder.checkBox.text = item.title

        holder.lnrCheck.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }

        holder.checkBox.isChecked = item.done

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onItemDoneStateListener.onItemStateChange(item, isChecked)
        }

    }

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(list: List<ToDo>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun getToDo(position: Int): ToDo {
        return dataList[position]
    }

    fun setOnItemClick(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemStateClick(onItemDoneStateListener: OnItemClickListener) {
        this.onItemDoneStateListener = onItemDoneStateListener
    }

    inner class ToDoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox = itemView.findViewById(R.id.chx)
        var lnrCheck: LinearLayout = itemView.findViewById(R.id.lnr_checked)
    }

}

interface OnItemClickListener {
    fun onItemClick(toDo: ToDo)
    fun onItemStateChange(toDo: ToDo, isChecked: Boolean)

}