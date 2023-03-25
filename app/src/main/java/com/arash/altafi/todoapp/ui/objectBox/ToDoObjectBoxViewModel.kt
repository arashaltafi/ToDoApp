package com.arash.altafi.todoapp.ui.objectBox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arash.altafi.todoapp.domain.objectBox.models.ToDoObjectBox
import com.arash.altafi.todoapp.domain.objectBox.repo.ToDoObjectBoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ToDoObjectBoxViewModel @Inject constructor(private var repository: ToDoObjectBoxRepository) :
    ViewModel() {

    private val _liveToDo: MutableLiveData<List<ToDoObjectBox>> = MutableLiveData(emptyList())
    val liveToDo: LiveData<List<ToDoObjectBox>> = _liveToDo

    init {
        getAllData()
    }

    fun insert(toDo: ToDoObjectBox) {
        repository.insertToDo(toDo)
    }

    fun delete(toDo: ToDoObjectBox) {
        if (toDo.id <= 0) return
        repository.deleteToDo(toDo)
    }

    fun update(toDo: ToDoObjectBox) {
        if (toDo.id <= 0) return
        repository.updateToDo(toDo)
    }

    fun getAllData() {
        _liveToDo.value = repository.getAllToDo()
    }

}