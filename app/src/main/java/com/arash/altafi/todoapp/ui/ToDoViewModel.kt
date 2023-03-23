package com.arash.altafi.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arash.altafi.todoapp.domain.models.ToDo
import com.arash.altafi.todoapp.domain.repositories.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(private var repository: ToDoRepository) : ViewModel() {

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