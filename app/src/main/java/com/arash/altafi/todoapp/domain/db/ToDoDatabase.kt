package com.arash.altafi.todoapp.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arash.altafi.todoapp.domain.dao.ToDoDao
import com.arash.altafi.todoapp.domain.models.ToDo

@Database(version = 1, entities = [ToDo::class], exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

}