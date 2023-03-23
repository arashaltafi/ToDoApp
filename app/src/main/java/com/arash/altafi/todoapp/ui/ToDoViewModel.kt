package com.arash.altafi.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arash.altafi.todoapp.domain.models.ToDo
import com.arash.altafi.todoapp.domain.repositories.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(private var repository: ToDoRepository) : ViewModel() {

    private val _liveToDo: MutableStateFlow<List<ToDo>> = MutableStateFlow(emptyList())
    val liveToDo: StateFlow<List<ToDo>> = _liveToDo

    init {
        viewModelScope.launch {
            repository.getAllToDo().collect {
                _liveToDo.value = it
            }
        }
    }

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

    suspend fun getAllData() {
        repository.getAllToDo().collect {
            _liveToDo.value = it
        }
    }

}