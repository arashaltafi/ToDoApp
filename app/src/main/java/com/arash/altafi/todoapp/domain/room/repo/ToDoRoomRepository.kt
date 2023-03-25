package com.arash.altafi.todoapp.domain.room.repo

import com.arash.altafi.todoapp.domain.room.dao.ToDoRoomDao
import com.arash.altafi.todoapp.domain.room.models.ToDoRoom
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRoomRepository @Inject constructor(private var toDoDao: ToDoRoomDao) {

    suspend fun insertToDo(toDo: ToDoRoom) {
        toDoDao.insertToDo(toDo)
    }

    suspend fun updateToDo(toDo: ToDoRoom) {
        toDoDao.updateToDo(toDo)
    }

    suspend fun deleteToDo(toDo: ToDoRoom) {
        toDoDao.deleteToDo(toDo)
    }

    fun getAllToDo(): Flow<List<ToDoRoom>> = toDoDao.getAllToDo()

    fun deleteAll() {
        toDoDao.deleteAll()
    }

}