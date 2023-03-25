package com.arash.altafi.todoapp.domain.objectBox.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ToDoObjectBox(
    @Id var id: Long = 0,
    var title: String,
    var description: String,
    var done: Boolean
)