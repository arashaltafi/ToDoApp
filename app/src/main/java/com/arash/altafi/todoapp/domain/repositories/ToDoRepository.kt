package com.arash.altafi.todoapp.domain.repositories

import com.arash.altafi.todoapp.domain.dao.ToDoDao
import com.arash.altafi.todoapp.domain.models.ToDo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepository @Inject constructor(private var toDoDao: ToDoDao) {

    suspend fun insertToDo(toDo: ToDo) {
        toDoDao.insertToDo(toDo)
    }

    suspend fun updateToDo(toDo: ToDo) {
        toDoDao.updateToDo(toDo)
    }

    suspend fun deleteToDo(toDo: ToDo) {
        toDoDao.deleteToDo(toDo)
    }

    fun getAllToDo(): Flow<List<ToDo>> = toDoDao.getAllToDo()

}