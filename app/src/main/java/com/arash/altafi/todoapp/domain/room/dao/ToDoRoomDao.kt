package com.arash.altafi.todoapp.domain.room.dao

import androidx.room.*
import com.arash.altafi.todoapp.domain.room.models.ToDoRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoRoomDao {

    // CURD => Create , Update , Read , Delete

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDoRoom)

    @Update
    suspend fun updateToDo(toDo: ToDoRoom)

    @Delete
    suspend fun deleteToDo(toDo: ToDoRoom)

    @Query("SELECT * FROM ToDo ORDER BY id ASC") //desc
    fun getAllToDo() : Flow<List<ToDoRoom>>

    @Query("DELETE FROM ToDo")
    fun deleteAll()

}