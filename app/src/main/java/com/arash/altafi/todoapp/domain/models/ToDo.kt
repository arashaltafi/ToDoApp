package com.arash.altafi.todoapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDo")
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo var title : String,
    @ColumnInfo var description : String,
    @ColumnInfo var done : Boolean
)