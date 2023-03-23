package com.arash.altafi.todoapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arash.altafi.todoapp.models.ToDo

@Dao
interface ToDoDao {

    // CURD => Create , Update , Read , Delete

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDo)

    @Update
    suspend fun updateToDo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)

    @Query("Select * from ToDo order by id asc") //desc
    fun getAllToDo() : LiveData<List<ToDo>>

}