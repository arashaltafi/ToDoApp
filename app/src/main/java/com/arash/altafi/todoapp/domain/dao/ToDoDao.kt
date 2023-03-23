package com.arash.altafi.todoapp.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arash.altafi.todoapp.domain.models.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    // CURD => Create , Update , Read , Delete

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDo)

    @Update
    suspend fun updateToDo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)

    @Query("SELECT * FROM ToDo ORDER BY id ASC") //desc
    fun getAllToDo() : Flow<List<ToDo>>

}