package com.arash.altafi.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arash.altafi.todoapp.dao.ToDoDao
import com.arash.altafi.todoapp.models.ToDo

@Database(version = 1 , entities = [ToDo::class])
abstract class ToDoDatabase : RoomDatabase() {

    companion object {
        private var database: ToDoDatabase? = null

        //multithreading with suspend fun
        fun getInstance(context: Context): ToDoDatabase {
            if (database == null) {
                database = Room.databaseBuilder(context, ToDoDatabase::class.java, "toDo.db")
                    .fallbackToDestructiveMigration().build()
                // fallbackToDestructiveMigration انتقال اطلاعات و دیتاهای جدید به دیتابیس با دستور
            }
            return database!!
        }
    }
    abstract fun toDoDao() : ToDoDao

}