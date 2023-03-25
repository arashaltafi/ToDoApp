package com.arash.altafi.todoapp.domain.objectBox.db

import android.content.Context
import com.arash.altafi.todoapp.domain.objectBox.models.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .name("toDoObjectBox")
            .build()
    }

}