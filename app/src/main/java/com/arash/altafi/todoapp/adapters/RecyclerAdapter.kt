package com.arash.altafi.todoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.todoapp.R
import com.arash.altafi.todoapp.models.ToDo

class RecyclerAdapter(private val context : Context) : RecyclerView.Adapter<ToDoHolder>() {

    private val dataList = ArrayList<ToDo>()
    lateinit var onItemClickListener: OnItemClickListener
    lateinit var onItemDoneStateListener: OnItemDoneStateListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false)
        return ToDoHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        val item = dataList.get(position)

        holder.checkBox.text = item.title

        holder.lnrCheck.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }

        holder.checkBox.isChecked = item.done

        holder.checkBox.setOnCheckedChangeListener { buttonView , isChecked ->
            onItemDoneStateListener.onItemStateChange(item , isChecked)
        }

    }

    override fun getItemCount(): Int =  dataList.size

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

    fun setOnItemStateClick(onItemDoneStateListener: OnItemDoneStateListener) {
        this.onItemDoneStateListener = onItemDoneStateListener
    }

}

class ToDoHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    var checkBox : CheckBox = itemView.findViewById(R.id.chx)
    var lnrCheck : LinearLayout = itemView.findViewById(R.id.lnr_checked)

}

interface OnItemClickListener {
    fun onItemClick(toDo: ToDo)
}

interface OnItemDoneStateListener {
    fun onItemStateChange(toDo: ToDo, isChecked: Boolean)
}