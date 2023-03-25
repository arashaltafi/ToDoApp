package com.arash.altafi.todoapp

import androidx.multidex.MultiDexApplication
import com.arash.altafi.todoapp.domain.objectBox.db.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        ObjectBox.init(this)
    }

}