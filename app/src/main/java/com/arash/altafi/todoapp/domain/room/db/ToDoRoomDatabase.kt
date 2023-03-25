package com.arash.altafi.todoapp.domain.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arash.altafi.todoapp.domain.room.dao.ToDoRoomDao
import com.arash.altafi.todoapp.domain.room.models.ToDoRoom

@Database(version = 1, entities = [ToDoRoom::class], exportSchema = false)
abstract class ToDoRoomDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoRoomDao

}