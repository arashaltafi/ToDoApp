package com.arash.altafi.todoapp.domain.repositories

import androidx.lifecycle.LiveData
import com.arash.altafi.todoapp.domain.dao.ToDoDao
import com.arash.altafi.todoapp.domain.models.ToDo
import javax.inject.Inject

class ToDoRepository @Inject constructor(private var toDoDao: ToDoDao) {

    private var allToDoList: LiveData<List<ToDo>> = toDoDao.getAllToDo()

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