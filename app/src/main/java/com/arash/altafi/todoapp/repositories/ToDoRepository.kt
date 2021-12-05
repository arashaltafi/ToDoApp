package com.arash.altafi.todoapp.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.arash.altafi.todoapp.dao.ToDoDao
import com.arash.altafi.todoapp.db.ToDoDatabase
import com.arash.altafi.todoapp.models.ToDo

class ToDoRepository(application: Application) {

    private var toDoDao : ToDoDao
    private var allToDoList : LiveData<List<ToDo>>

    init {
        val database = ToDoDatabase.getInstance(application)
        toDoDao = database.toDoDao()
        allToDoList = toDoDao.getAllToDo()
    }

    suspend fun insertToDo(toDo: ToDo) {
        toDoDao.insertToDo(toDo)
    }

    suspend fun updateToDo(toDo: ToDo) {
        toDoDao.updateToDo(toDo)
    }

    suspend fun deleteToDo(toDo: ToDo) {
        toDoDao.deleteToDo(toDo)
    }

    fun getAllToDo(): LiveData<List<ToDo>> {
        return allToDoList
    }

}