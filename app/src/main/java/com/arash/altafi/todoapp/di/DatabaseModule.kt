package com.arash.altafi.todoapp.di

import android.content.Context
import androidx.room.Room
import com.arash.altafi.todoapp.domain.dao.ToDoDao
import com.arash.altafi.todoapp.domain.db.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun getRoomDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(context, ToDoDatabase::class.java, "toDo.db")
            .fallbackToDestructiveMigration()
            .build()

        //allowMainThreadQueries()
        //createFromAsset("database/myapp.db")  // For db first
        // fallbackToDestructiveMigration انتقال اطلاعات و دیتاهای جدید به دیتابیس با دستور
    }

    @Singleton
    @Provides
    fun getUserDao(templateDatabase: ToDoDatabase): ToDoDao {
        return templateDatabase.toDoDao()
    }
}