package com.arash.altafi.todoapp.ui.objectBox.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.todoapp.R
import com.arash.altafi.todoapp.domain.objectBox.models.ToDoObjectBox

class RecyclerAdapterObjectBox : RecyclerView.Adapter<RecyclerAdapterObjectBox.ToDoHolder>() {

    private val dataList = ArrayList<ToDoObjectBox>()
    var onItemStateChange: ((ToDoObjectBox, Boolean) -> Unit)? = null
    var onItemClick: ((ToDoObjectBox) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ToDoHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        val item = dataList[position]

        holder.checkBox.text = item.title

        holder.lnrCheck.setOnClickListener {
            onItemClick?.invoke(item)
        }

        holder.checkBox.isChecked = item.done

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onItemStateChange?.invoke(item, isChecked)
        }

    }

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(list: List<ToDoObjectBox>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun getToDo(position: Int): ToDoObjectBox = dataList[position]

    inner class ToDoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox = itemView.findViewById(R.id.chx)
        var lnrCheck: LinearLayout = itemView.findViewById(R.id.lnr_checked)
    }

}