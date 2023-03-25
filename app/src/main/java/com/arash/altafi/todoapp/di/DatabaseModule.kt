package com.arash.altafi.todoapp.di

import android.content.Context
import androidx.room.Room
import com.arash.altafi.todoapp.domain.room.dao.ToDoRoomDao
import com.arash.altafi.todoapp.domain.room.db.ToDoRoomDatabase
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
    fun getRoomDatabase(@ApplicationContext context: Context): ToDoRoomDatabase {
        return Room.databaseBuilder(context, ToDoRoomDatabase::class.java, "toDoRoom.db")
            .fallbackToDestructiveMigration()
            .build()

        //allowMainThreadQueries()
        //createFromAsset("database/myapp.db")  // For db first
        // fallbackToDestructiveMigration انتقال اطلاعات و دیتاهای جدید به دیتابیس با دستور
    }

    @Singleton
    @Provides
    fun getUserDao(templateDatabase: ToDoRoomDatabase): ToDoRoomDao {
        return templateDatabase.toDoDao()
    }
}