package com.arash.altafi.todoapp.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arash.altafi.todoapp.domain.room.models.ToDoRoom
import com.arash.altafi.todoapp.domain.room.repo.ToDoRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoRoomViewModel @Inject constructor(private var repository: ToDoRoomRepository) : ViewModel() {

    private val _liveToDo: MutableStateFlow<List<ToDoRoom>> = MutableStateFlow(emptyList())
    val liveToDo: StateFlow<List<ToDoRoom>> = _liveToDo

    init {
        viewModelScope.launch {
            getAllData()
        }
    }

    suspend fun insert(toDo: ToDoRoom) {
        repository.insertToDo(toDo)
    }

    suspend fun delete(toDo: ToDoRoom) {
        if (toDo.id <= 0) return
        repository.deleteToDo(toDo)
    }

    suspend fun update(toDo: ToDoRoom) {
        if (toDo.id <= 0) return
        repository.updateToDo(toDo)
    }

    suspend fun getAllData() {
        repository.getAllToDo().collect {
            _liveToDo.value = it
        }
    }

}