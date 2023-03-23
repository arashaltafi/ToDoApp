package com.arash.altafi.todoapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arash.altafi.todoapp.adapters.OnItemClickListener
import com.arash.altafi.todoapp.adapters.RecyclerAdapter
import com.arash.altafi.todoapp.handlers.SwipeToDeleteCallback
import com.arash.altafi.todoapp.models.ToDo
import com.arash.altafi.todoapp.viewModels.ToDoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var swipe: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init() {
        bindViews()

        adapter = RecyclerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        toDoViewModel.getAllData().observe(this) {
            adapter.setDataList(it)
        }

        val addNewToDoResultActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data = it.data ?: return@registerForActivityResult
                    val title = data.getStringExtra(AddToDoActivity.EXTRA_TITLE)
                    val description = data.getStringExtra(AddToDoActivity.EXTRA_DESC)
                    val toDo =
                        ToDo(title = title!!, description = description!!, done = false)
                    CoroutineScope(Dispatchers.Main).launch { toDoViewModel.insert(toDo) }
                    Toast.makeText(this, "New Todo Added", Toast.LENGTH_SHORT).show()
                }
            }

        fab.setOnClickListener {
            val intent = Intent(this, AddToDoActivity::class.java)
            addNewToDoResultActivity.launch(intent)
        }

        swipe.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

        val swipeHandler = object : SwipeToDeleteCallback(this, toDoViewModel, adapter) {}
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val editToDoResultActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data = it.data ?: return@registerForActivityResult
                    val id = data.getIntExtra(AddToDoActivity.EXTRA_ID, -1)
                    if (id == -1) {
                        Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
                    } else {
                        val title = data.getStringExtra(AddToDoActivity.EXTRA_TITLE)
                        val description = data.getStringExtra(AddToDoActivity.EXTRA_DESC)
                        val toDo = ToDo(
                            id = id,
                            title = title!!,
                            description = description!!,
                            done = false
                        )
                        CoroutineScope(Dispatchers.Main).launch { toDoViewModel.update(toDo) }
                        MainScope().launch { toDoViewModel.insert(toDo) }
                        Toast.makeText(this, "Todo Updated", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        adapter.setOnItemClick(object : OnItemClickListener {
            override fun onItemClick(toDo: ToDo) {
                val intent = Intent(this@MainActivity, AddToDoActivity::class.java)
                intent.putExtra(AddToDoActivity.EXTRA_ID, toDo.id)
                intent.putExtra(AddToDoActivity.EXTRA_TITLE, toDo.title)
                intent.putExtra(AddToDoActivity.EXTRA_DESC, toDo.description)
                editToDoResultActivity.launch(intent)
            }

            override fun onItemStateChange(toDo: ToDo, isChecked: Boolean) {
                toDo.done = isChecked
                MainScope().launch { toDoViewModel.update(toDo) }
            }
        })
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.rc_todo)
        fab = findViewById(R.id.fab_add)
        swipe = findViewById(R.id.swip_refresh_layout)
    }

}