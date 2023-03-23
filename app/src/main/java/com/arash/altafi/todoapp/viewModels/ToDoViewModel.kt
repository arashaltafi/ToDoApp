package com.arash.altafi.todoapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arash.altafi.todoapp.models.ToDo
import com.arash.altafi.todoapp.repositories.ToDoRepository

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: ToDoRepository = ToDoRepository(application)

    suspend fun insert(toDo: ToDo) {
        repository.insertToDo(toDo)
    }

    suspend fun delete(toDo: ToDo) {
        if (toDo.id <= 0) return
        repository.deleteToDo(toDo)
    }

    suspend fun update(toDo: ToDo) {
        if (toDo.id <= 0) return
        repository.updateToDo(toDo)
    }

    fun getAllData(): LiveData<List<ToDo>> {
        return repository.getAllToDo()
    }

}