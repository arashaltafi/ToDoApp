package com.arash.altafi.todoapp.domain.objectBox.repo

import com.arash.altafi.todoapp.domain.objectBox.db.ObjectBox
import com.arash.altafi.todoapp.domain.objectBox.models.ToDoObjectBox
import com.arash.altafi.todoapp.domain.objectBox.models.ToDoObjectBox_
import io.objectbox.android.ObjectBoxLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ToDoObjectBoxRepository @Inject constructor() {

    private val coroutineContext: CoroutineContext get() = Dispatchers.Main

    fun insertToDo(toDo: ToDoObjectBox) = CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
        ObjectBox.boxStore.boxFor(ToDoObjectBox::class.java).put(toDo)
    }


    fun updateToDo(toDo: ToDoObjectBox) = CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
        ObjectBox.boxStore.boxFor(ToDoObjectBox::class.java).put(toDo)
    }

    fun deleteToDo(toDo: ToDoObjectBox) = CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
        val userBox = ObjectBox.boxStore.boxFor(ToDoObjectBox::class.java)
        userBox.remove(toDo)
    }

    fun getAllToDo(): MutableList<ToDoObjectBox> {
        val usersQuery = ObjectBox.boxStore.boxFor(ToDoObjectBox::class.java).query()
            .orderDesc(ToDoObjectBox_.id)
            .build()
        return usersQuery.use { it.find() }
//        return ObjectBoxLiveData(usersQuery)
    }

    private fun getToDo(): ObjectBoxLiveData<ToDoObjectBox> {
        val usersQuery = ObjectBox.boxStore.boxFor(ToDoObjectBox::class.java).query()
            .orderDesc(ToDoObjectBox_.id)
            .build()
        return ObjectBoxLiveData(usersQuery)
    }

    fun deleteAll() {
        ObjectBox.boxStore.boxFor(ToDoObjectBox::class.java).removeAll()
    }

}